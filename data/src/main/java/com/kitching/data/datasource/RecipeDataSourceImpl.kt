package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kitching.data.dto.IngredientDTO
import com.kitching.data.dto.RecipeDTO
import com.kitching.data.firebase.COLLECTION_INGREDIENT
import com.kitching.data.firebase.COLLECTION_RECIPE
import kotlinx.coroutines.tasks.await

class RecipeDataSourceImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) : RecipeDataSource {
    override suspend fun getRecipes(teamId: String): List<RecipeDTO> {
        val recipeDocuments = db.collection(COLLECTION_RECIPE)
            .whereEqualTo("teamId", teamId)
            .get()
            .await()

        return recipeDocuments.documents.mapNotNull { documentSnapshot ->
            val recipeDTO = documentSnapshot.toObject(RecipeDTO::class.java)

            val ingredientsSnapshot = documentSnapshot.reference
                .collection(COLLECTION_INGREDIENT)
                .get()
                .await()

            val ingredients = ingredientsSnapshot.toObjects(IngredientDTO::class.java)

            recipeDTO?.copy(ingredient = ingredients)
        }.toList()
    }

    override suspend fun getRecipeById(recipeId: String): RecipeDTO {
        val documentSnapshot = db.collection(COLLECTION_RECIPE)
            .document(recipeId)
            .get()
            .await()

        val recipeDTO = documentSnapshot.toObject(RecipeDTO::class.java)!!

        val ingredientsSnapshot = documentSnapshot.reference
            .collection(COLLECTION_INGREDIENT)
            .get()
            .await()

        val ingredients = ingredientsSnapshot.toObjects(IngredientDTO::class.java)

        return recipeDTO.copy(ingredient = ingredients)
    }

    override suspend fun updateRecipe(
        recipeId: String,
        name: String,
        steps: List<String>,
        ingredients: List<IngredientDTO>,
    ): Boolean = runCatching {
        val recipeRef = db.collection(COLLECTION_RECIPE).document(recipeId)

        // 1) 레시피 문서 업데이트
        recipeRef.update(
            mapOf(
                "name" to name,
                "steps" to steps
            )
        ).await()

        // 2) 기존 ingredient 전부 삭제
        val oldIngredients = recipeRef.collection(COLLECTION_INGREDIENT).get().await()
        for (doc in oldIngredients.documents) {
            doc.reference.delete().await()
        }

        // 3) 새로 입력된 재료 목록 추가
        ingredients.forEach { ingredients ->
            val data = mapOf(
                "id" to ingredients.id,
                "name" to ingredients.name,
                "once" to ingredients.once,
                "twice" to ingredients.twice,
                "unit" to ingredients.unit
            )

            val ingDoc = recipeRef.collection(COLLECTION_INGREDIENT).add(data).await()

            recipeRef.collection(COLLECTION_INGREDIENT)
                .document(ingDoc.id)
                .update("id", ingDoc.id)
                .await()
        }
    }.isSuccess

    override suspend fun uploadImageToStorage(
        imageData: ByteArray,
        imageName: String,
    ): Result<String> = runCatching {
        val storageRef = storage.reference.child("recipeImage/$imageName")

        storageRef.putBytes(imageData).await()

        storageRef.downloadUrl.await().toString()
    }

    override suspend fun saveRecipe(
        name: String,
        picture: String,
        steps: List<String>,
        teamId: String,
    ): Result<String> = runCatching {
        val recipeData = mapOf(
            "id" to "",
            "name" to name,
            "picture" to picture,
            "steps" to steps,
            "teamId" to teamId
        )

        val recipeDocument = db.collection(COLLECTION_RECIPE)
            .add(recipeData)
            .await()

        db.collection(COLLECTION_RECIPE)
            .document(recipeDocument.id)
            .update("id", recipeDocument.id)
            .await()

        recipeDocument.id
    }

    override suspend fun saveIngredients(
        recipeId: String,
        ingredients: List<Map<String, String>>,
    ): Boolean = runCatching {
        val ingredientCollection = db.collection(COLLECTION_RECIPE)
            .document(recipeId)
            .collection(COLLECTION_INGREDIENT)

        ingredients.forEach { ingredient ->
            val ingredientData = ingredient.mapValues { (key, value) ->
                when (key) {
                    "once", "twice" -> value.toIntOrNull() ?: -1
                    else -> value
                }
            }
            val ingredientDocument = ingredientCollection.add(ingredientData).await()
            ingredientCollection.document(ingredientDocument.id)
                .update("id", ingredientDocument.id)
                .await()
        }
    }.isSuccess
}