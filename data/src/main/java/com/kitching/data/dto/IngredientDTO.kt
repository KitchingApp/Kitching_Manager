package com.kitching.data.dto

import com.kitching.domain.entities.Ingredient

data class IngredientDTO(
    val id: String = "",
    val name: String = "",
    val once: Int = -1,
    val twice: Int = -1,
    val unit: String = ""
) {
    fun toDomain(): Ingredient {
        return Ingredient(
            ingredientId = id,
            ingredientName = name,
            once = once,
            twice = twice,
            unit = unit
        )
    }

    companion object {
        fun domainToDto(domain: Ingredient): IngredientDTO = IngredientDTO(
            id = domain.ingredientId,
            name = domain.ingredientName,
            once = domain.once,
            twice = domain.twice,
            unit = domain.unit
        )
    }
}
