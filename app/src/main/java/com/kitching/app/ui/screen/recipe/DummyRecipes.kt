package com.kitching.app.ui.screen.recipe

import com.kitching.app.R

data class Recipe(
    val id: String = "",
    val name: String = "",
    val picture: Int = -1,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<String> = emptyList(),
    val teamId: String = ""
)

data class Ingredient(
    val id: String = "",
    val name: String = "",
    val once: Int = -1,
    val twice: Int = -1,
    val each: String = ""
)

val dummyRecipes = listOf(
    Recipe(
        id = "1",
        name = "팬케이크",
        picture = R.drawable.pancake,
        ingredients = listOf(
            Ingredient(name = "밀가루", once = 200, each = "g"),
            Ingredient(name = "우유", once = 250, each = "ml"),
            Ingredient(name = "계란", once = 2, each = "개")
        ),
        steps = listOf("밀가루와 우유를 섞는다.", "계란을 넣고 반죽한다.", "팬에서 구워 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "2",
        name = "오믈렛",
        picture = R.drawable.chestnutcream,
        ingredients = listOf(
            Ingredient(name = "계란", once = 3, each = "개"),
            Ingredient(name = "우유", once = 50, each = "ml"),
            Ingredient(name = "버터", once = 10, each = "g")
        ),
        steps = listOf("계란과 우유를 섞는다.", "팬에 버터를 녹인다.", "계란을 익혀 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "3",
        name = "팬케이크",
        picture = R.drawable.pancake,
        ingredients = listOf(
            Ingredient(name = "밀가루", once = 200, each = "g"),
            Ingredient(name = "우유", once = 250, each = "ml"),
            Ingredient(name = "계란", once = 2, each = "개")
        ),
        steps = listOf("밀가루와 우유를 섞는다.", "계란을 넣고 반죽한다.", "팬에서 구워 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "4",
        name = "오믈렛",
        picture = R.drawable.chestnutcream,
        ingredients = listOf(
            Ingredient(name = "계란", once = 3, each = "개"),
            Ingredient(name = "우유", once = 50, each = "ml"),
            Ingredient(name = "버터", once = 10, each = "g")
        ),
        steps = listOf("계란과 우유를 섞는다.", "팬에 버터를 녹인다.", "계란을 익혀 완성한다."),
        teamId = "team_1"
    ),Recipe(
        id = "5",
        name = "팬케이크",
        picture = R.drawable.pancake,
        ingredients = listOf(
            Ingredient(name = "밀가루", once = 200, each = "g"),
            Ingredient(name = "우유", once = 250, each = "ml"),
            Ingredient(name = "계란", once = 2, each = "개")
        ),
        steps = listOf("밀가루와 우유를 섞는다.", "계란을 넣고 반죽한다.", "팬에서 구워 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "6",
        name = "오믈렛",
        picture = R.drawable.chestnutcream,
        ingredients = listOf(
            Ingredient(name = "계란", once = 3, each = "개"),
            Ingredient(name = "우유", once = 50, each = "ml"),
            Ingredient(name = "버터", once = 10, each = "g")
        ),
        steps = listOf("계란과 우유를 섞는다.", "팬에 버터를 녹인다.", "계란을 익혀 완성한다."),
        teamId = "team_1"
    ),Recipe(
        id = "7",
        name = "팬케이크",
        picture = R.drawable.pancake,
        ingredients = listOf(
            Ingredient(name = "밀가루", once = 200, each = "g"),
            Ingredient(name = "우유", once = 250, each = "ml"),
            Ingredient(name = "계란", once = 2, each = "개")
        ),
        steps = listOf("밀가루와 우유를 섞는다.", "계란을 넣고 반죽한다.", "팬에서 구워 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "8",
        name = "오믈렛",
        picture = R.drawable.chestnutcream,
        ingredients = listOf(
            Ingredient(name = "계란", once = 3, each = "개"),
            Ingredient(name = "우유", once = 50, each = "ml"),
            Ingredient(name = "버터", once = 10, each = "g")
        ),
        steps = listOf("계란과 우유를 섞는다.", "팬에 버터를 녹인다.", "계란을 익혀 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "9",
        name = "팬케이크",
        picture = R.drawable.pancake,
        ingredients = listOf(
            Ingredient(name = "밀가루", once = 200, each = "g"),
            Ingredient(name = "우유", once = 250, each = "ml"),
            Ingredient(name = "계란", once = 2, each = "개")
        ),
        steps = listOf("밀가루와 우유를 섞는다.", "계란을 넣고 반죽한다.", "팬에서 구워 완성한다."),
        teamId = "team_1"
    ),
    Recipe(
        id = "10",
        name = "오믈렛",
        picture = R.drawable.chestnutcream,
        ingredients = listOf(
            Ingredient(name = "계란", once = 3, each = "개"),
            Ingredient(name = "우유", once = 50, each = "ml"),
            Ingredient(name = "버터", once = 10, each = "g")
        ),
        steps = listOf("계란과 우유를 섞는다.", "팬에 버터를 녹인다.", "계란을 익혀 완성한다."),
        teamId = "team_1"
    )
)
