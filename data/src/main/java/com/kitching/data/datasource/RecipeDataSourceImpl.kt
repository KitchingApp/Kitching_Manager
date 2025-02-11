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

    override suspend fun createRecipe(
        imageData: ByteArray?,
        imageName: String,
        recipeName: String,
        steps: List<String>,
        teamId: String,
        ingredients: List<Map<String, String>>,
    ): Boolean = runCatching {
        // (1) 이미지 업로드
        val pictureUrl = if (imageData != null) {
            val storageRef = storage.reference.child("recipeImage/$imageName")
            storageRef.putBytes(imageData).await()
            storageRef.downloadUrl.await().toString()
        } else {
            ""
        }

        // (2) 레시피 생성
        val recipeData = mapOf(
            "id" to "",
            "name" to recipeName,
            "picture" to pictureUrl,
            "steps" to steps,
            "teamId" to teamId
        )

        val recipeDocument = db.collection(COLLECTION_RECIPE)
            .add(recipeData)
            .await()

        // 문서 id 필드 업데이트
        db.collection(COLLECTION_RECIPE)
            .document(recipeDocument.id)
            .update("id", recipeDocument.id)
            .await()

        val recipeId = recipeDocument.id

        // (3) 재료 저장
        val ingredientCollection = db.collection(COLLECTION_RECIPE)
            .document(recipeId)
            .collection(COLLECTION_INGREDIENT)

        ingredients.forEach { ingredient ->
            val mapped = ingredient.mapValues { (key, value) ->
                when (key) {
                    "once", "twice" -> value.toIntOrNull() ?: -1
                    else -> value
                }
            }
            val ingredientDoc = ingredientCollection.add(mapped).await()
            ingredientCollection.document(ingredientDoc.id)
                .update("id", ingredientDoc.id)
                .await()
        }
    }.isSuccess
}