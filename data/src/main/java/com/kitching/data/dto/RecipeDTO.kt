package com.kitching.data.dto

import com.kitching.domain.entities.Recipe

data class RecipeDTO(
    val id: String = "",
    val teamId: String = "",
    val name: String = "",
    val picture: String = "",
    val ingredient: List<IngredientDTO> = emptyList(),
    val steps: List<String> = emptyList(),
) {
    /**
     * ingredient가 없는 업로드용 data class(업로드 시 하위 컬렉션으로 들어가기 때문)
     *
     */
    inner class RecipeCreateDTO(
        val id: String = "",
        val teamId: String = "",
        val name: String = "",
        val picture: String = "",
        val steps: List<String> = emptyList(),
    )

    fun toDomain(): Recipe {
        return Recipe(
            recipeId = id,
            recipeName = name,
            picture = picture,
            ingredient = ingredient.map { it.toDomain() },
            steps = steps
        )
    }

    fun toCreateDTO(): RecipeCreateDTO = RecipeCreateDTO(
        id = id,
        teamId = teamId,
        name = name,
        picture = picture,
        steps = steps
    )
}