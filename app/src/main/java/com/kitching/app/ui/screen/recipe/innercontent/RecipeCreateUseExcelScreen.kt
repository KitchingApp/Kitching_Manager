package com.kitching.app.ui.screen.recipe.innercontent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.KitchingApplication
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.service.RecipeUploadService
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.RecipeSheetInfoExpandableCardItem
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.entities.Ingredient
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.poi.hssf.usermodel.HSSFPatriarch
import org.apache.poi.hssf.usermodel.HSSFPicture
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFDrawing
import org.apache.poi.xssf.usermodel.XSSFPicture
import org.apache.poi.xssf.usermodel.XSSFSheet
import java.io.File
import java.util.UUID


data class RecipeSheetInfo(
    val sheetName: String,
    val imageData: ByteArray?,
    val imageName: String,
    val recipeName: String,
    val ingredients: List<Ingredient>,
    val recipeSteps: List<String>
) {
    fun toRecipeData() = RecipeData(
        imageData = imageData,
        imageName = imageName,
        recipeName = recipeName,
        ingredients = ingredients.map { IngredientData.domainToParcelize(it) },
        recipeSteps = recipeSteps
    )
}

@Serializable
@Parcelize
data class RecipeServiceData(
    val recipes: List<RecipeData>,
    val teamId: String
) : Parcelable

@Serializable
@Parcelize
data class RecipeData(
    val imageData: ByteArray?,
    val imageName: String,
    val recipeName: String,
    val ingredients: List<IngredientData>,
    val recipeSteps: List<String>
) : Parcelable

@Serializable
@Parcelize
data class IngredientData(
    val ingredientId: String,
    val ingredientName: String,
    val once: Int,
    val twice: Int,
    val unit: String,
) : Parcelable {
    companion object {
        fun domainToParcelize(domain: Ingredient) = IngredientData(
            ingredientId = domain.ingredientId,
            ingredientName = domain.ingredientName,
            once = domain.once,
            twice = domain.twice,
            unit = domain.unit
        )
    }

    fun toDomain() = Ingredient(
        ingredientId = ingredientId,
        ingredientName = ingredientName,
        once = once,
        twice = twice,
        unit = unit
    )
}

@Composable
fun RecipeCreateUseExcelScreen(
    commonState: CommonState,
    navigateToRecipeUploadInProgress: () -> Unit,
    viewModel: RecipeViewModel = viewModel(factory = viewModelFactory)
) {
    var recipeInfos by remember { mutableStateOf((emptyList<RecipeSheetInfo>())) }
    var selectedRecipes by remember { mutableStateOf(emptyList<Int>()) }
    var teamId by remember { mutableStateOf("") }

    val recipeCreateResultState by viewModel.createRecipeResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore(commonState.navController.context).getTeamId()
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {
            commonState.navController.popBackStack()
        },
        actionIconInfo = ActionIconInfo.CHECK,
        onClickActionIcon = {
            val file = File(KitchingApplication.getInstance().cacheDir, "recipe_data.json")
            file.writeText(Json.encodeToString(RecipeServiceData(
                teamId = teamId,
                recipes = selectedRecipes.map { recipeInfos[it].toRecipeData() }
            )))
            val fileUri = FileProvider.getUriForFile(
                commonState.navController.context,
                "com.kitching.app.fileprovider",
                file
            )
            val intent = Intent(
                commonState.navController.context,
                RecipeUploadService::class.java
            )
                .setData(fileUri)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            commonState.navController.context.startForegroundService(
                intent
            )
            navigateToRecipeUploadInProgress()
        }
    )

    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            recipeInfos = emptyList()
            uri?.let {
                val excelWorkBook = readExcelFile(KitchingApplication.getInstance(), it)
                val sheetIterator = excelWorkBook.sheetIterator()
                while (sheetIterator.hasNext()) {
                    val sheet = sheetIterator.next()
                    val sheetName = sheet.sheetName
                    val recipeName = runCatching { getRecipeTitle(sheet) }.getOrElse { "" }
                    val ingredients =
                        runCatching { getIngredients(sheet) }.getOrElse { emptyList() }
                    val recipeSteps = runCatching { getSteps(sheet) }.getOrElse { emptyList() }
                    val fileName = UUID.randomUUID().toString().replace("-", "")
                    val imageUri = runCatching { getImageFromSheet(sheet) }.getOrNull()
                    val newRecipeInfos = recipeInfos.toMutableList()
                    newRecipeInfos.add(
                        RecipeSheetInfo(
                            sheetName = sheetName,
                            imageData = imageUri,
                            imageName = fileName,
                            recipeName = recipeName,
                            ingredients = ingredients,
                            recipeSteps = recipeSteps
                        )
                    )
                    recipeInfos = newRecipeInfos.toList()
                }
            }
        }
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(defaultPadding)
            ) {
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        pickFileLauncher.launch(
                            arrayOf(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // XLSX
                                "application/vnd.ms-excel", // XLS
                            )
                        )
                    }
                ) {
                    Text("엑셀 파일 선택")
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(defaultPadding)
                ) {
                    recipeInfos.forEachIndexed { index, recipeInfo ->
                        item(key = recipeInfo.sheetName) {
                            RecipeSheetInfoExpandableCardItem(recipeInfo) {
                                val newList = selectedRecipes.toMutableList()
                                if (newList.contains(index)) {
                                    newList.remove(index)
                                } else {
                                    newList.add(index)
                                }
                                selectedRecipes = newList
                            }
                        }
                    }
                }
            }
        }
    }
}

fun readExcelFile(context: Context, uri: Uri): Workbook =
    WorkbookFactory.create(context.contentResolver.openInputStream(uri))

fun getRecipeTitle(sheet: Sheet): String = sheet.getRow(0).getCell(0).toString()

fun getIngredients(sheet: Sheet): List<Ingredient> {
    val ingredients = mutableListOf<Ingredient>()
    var rowNum = 3
    while (true) {
        val currentRow = sheet.getRow(rowNum)
        val once = currentRow.getCell(1).toString().ifBlank { "0" }
        val twice = currentRow.getCell(2).toString().ifBlank { "0" }
        val unit = currentRow.getCell(3).toString()
        val ingredientName = currentRow.getCell(4).toString()
        if (ingredientName.isBlank()) break
        ingredients.add(
            Ingredient(
                ingredientId = "",
                ingredientName = ingredientName,
                once = once.toFloat().toInt(),
                twice = twice.toFloat().toInt(),
                unit = unit
            )
        )
        rowNum++
    }
    return ingredients
}

fun getSteps(sheet: Sheet): List<String> {
    val steps = mutableListOf<String>()
    var rowNum = 3
    while (true) {
        val currentRow = sheet.getRow(rowNum)
        val step = currentRow.getCell(6).toString()
        if (step.isBlank()) break
        steps.add(step)
        rowNum++
    }
    return steps
}

fun getImageFromSheet(sheet: Sheet): ByteArray {
    val images = mutableListOf<ByteArray>()

    if (sheet is XSSFSheet) {
        val relations = sheet.getRelations()

        for (relation in relations) {
            if (relation is XSSFDrawing) {
                for (anchor in relation.getShapes()) {
                    if (anchor is XSSFPicture) {
                        val pictureData = anchor.pictureData
                        images.add(pictureData.data)
                    }
                }
            }
        }
    } else if (sheet is HSSFSheet) {
        val patriarch: HSSFPatriarch = sheet.drawingPatriarch
        val shapes = patriarch.children

        shapes.forEach { shape ->
            if (shape is HSSFPicture) {
                return shape.pictureData.data
            }
        }
    }

    return images[0]
}