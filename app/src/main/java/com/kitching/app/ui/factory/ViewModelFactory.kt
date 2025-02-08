package com.kitching.app.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitching.app.common.KitchingApplication
import com.kitching.app.ui.model.PrepCategoryViewModel
import com.kitching.app.ui.model.PrepViewModel
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.model.ScheduleTimeViewModel
import com.kitching.app.ui.model.ScheduleViewModel
import com.kitching.app.util.PreferencesDataStore
import com.kitching.data.repository.PrepCategoryRepositoryImpl
import com.kitching.data.repository.PrepRepositoryImpl
import com.kitching.data.repository.RecipeRepositoryImpl
import com.kitching.data.repository.ScheduleRepositoryImpl
import com.kitching.data.repository.ScheduleTimeRepositoryImpl
import com.kitching.data.repository.UserTeamRepositoryImpl

@Suppress("UNCHECKED_CAST")
val viewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(PrepViewModel::class.java) ->
                    PrepViewModel(PrepRepositoryImpl())
                isAssignableFrom(PrepCategoryViewModel::class.java) ->
                    PrepCategoryViewModel(PrepCategoryRepositoryImpl())
                isAssignableFrom(ScheduleTimeViewModel::class.java) ->
                    ScheduleTimeViewModel(
                        repository = ScheduleTimeRepositoryImpl()
                    )
                isAssignableFrom(ScheduleViewModel::class.java) ->
                    ScheduleViewModel(
                        scheduleTimeRepository = ScheduleTimeRepositoryImpl(),
                        userTeamRepository = UserTeamRepositoryImpl(),
                        scheduleRepository = ScheduleRepositoryImpl()
                    )
//                isAssignableFrom(DepartmentViewModel::class.java) ->
//                    DepartmentViewModel()
//                isAssignableFrom(NoticeViewModel::class.java) ->
//                    NoticeViewModel()
//                isAssignableFrom(OrderViewModel::class.java) ->
//                    OrderViewModel()
                isAssignableFrom(RecipeViewModel::class.java) ->
                    RecipeViewModel(
                        recipeRepository = RecipeRepositoryImpl(),
                        dataStore = PreferencesDataStore(context = KitchingApplication.getInstance())
                    )
//                isAssignableFrom(TeamViewModel::class.java) ->
//                    TeamViewModel()
//                isAssignableFrom(LoginViewModel::class.java) ->
//                    LoginViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}