package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.IngredientDTO
import com.kitching.data.dto.RecipeDTO
import com.kitching.data.firebase.COLLECTION_INGREDIENT
import com.kitching.data.firebase.COLLECTION_RECIPE
import kotlinx.coroutines.tasks.await

class RecipeDataSourceImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
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
}