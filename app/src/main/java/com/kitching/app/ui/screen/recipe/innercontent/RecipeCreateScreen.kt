package com.kitching.app.ui.screen.recipe.innercontent

import android.annotation.SuppressLint
import android.net.Uri
import com.kitching.app.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.AppResultHandler
import com.kitching.app.common.CommonState
import com.kitching.app.common.KitchingApplication
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.common.showToast
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.entities.Ingredient
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecipeCreateScreen(
    commonState: CommonState,
    recipeViewModel: RecipeViewModel = viewModel(factory = viewModelFactory)
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imgName by remember { mutableStateOf("") }
    var recipeName by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>(Ingredient.init()))}
    var recipeSteps by remember { mutableStateOf(listOf("")) }
    var teamId by remember { mutableStateOf("") }

    commonState.coroutineScope.launch {
        teamId = PreferencesDataStore(commonState.navController.context).getTeamId().toString()
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "레시피 추가",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {
            commonState.navController.popBackStack()
        },
        actionIconInfo = ActionIconInfo.CHECK,
        onClickActionIcon = {
            // (1) 여기서 createRecipe 호출
            val context = KitchingApplication.getInstance()
            // Uri -> ByteArray 변환
            val imageData: ByteArray? = imageUri?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            }

            recipeViewModel.createRecipe(
                imageData = imageData,
                imageName = imgName,
                recipeName = recipeName,
                steps = recipeSteps,
                teamId = teamId,
                ingredients = ingredients
            )
        }
    )

    // ViewModel의 StateFlow 관찰 (로딩/성공/실패)
    val uploadState by recipeViewModel.createRecipeResult.collectAsStateWithLifecycle()

    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            imageUri = uri
            imgName = UUID.randomUUID().toString().replace("-", "")
        } else {
            showToast("이미지를 못 불러 왔습니다.")
        }
    }

    fun launchPhotoPicker() {
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 27.dp),
                userScrollEnabled = true,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                /** 이미지 업로드 박스 */
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                            .border(1.dp, NeutralGray500)
                            .clickable { launchPhotoPicker() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUri != null) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Selected Recipe Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.logo_dish),
                                    contentDescription = "Empty Img"
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth().padding(15.dp),
                                    text = "레시피 이미지를 추가해주세요.",
                                    style = H3_m,
                                    textAlign = TextAlign.Center,
                                    color = NeutralGray300
                                )
                            }
                        }
                    }
                }

                /** 레시피 이름 입력 */
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        BasicTextField(
                            value = recipeName,
                            onValueChange = { recipeName = it },
                            textStyle = H2.copy(color = NeutralGray800),
                            modifier = Modifier
                                .fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (recipeName.isEmpty()) {
                                        Text(
                                            text = "레시피 이름을 입력하세요.",
                                            style = H2,
                                            color = NeutralGray300
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                /** `CreateIngredientsTable` 사용 */
                item {
                    CreateIngredientsTable(
                        ingredients = ingredients,
                        onIngredientsChange = { newList ->
                            ingredients = newList
                        }
                    )
                }

                /** `CreateStepTable` 사용 */
                item {
                    CreateStepTable(
                        steps = recipeSteps,
                        onStepsChange = { recipeSteps = it.toMutableList() }
                    )
                }
            }
            // (3) 로딩/에러 UI 표시
            AppResultHandler(
                state = uploadState,
                onFailure = { error ->
                    showToast(error.message.toString())
                },
                onSuccess = {
                    showToast("레시피 업로드 성공!")
                    commonState.navController.popBackStack()
                }
            )

        }
    }
}