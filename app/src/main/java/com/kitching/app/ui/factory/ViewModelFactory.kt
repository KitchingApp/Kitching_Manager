package com.kitching.app.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitching.app.ui.model.PrepCategoryViewModel
import com.kitching.app.ui.model.PrepViewModel
import com.kitching.data.repository.PrepCategoryRepositoryImpl
import com.kitching.data.repository.PrepRepositoryImpl

@Suppress("UNCHECKED_CAST")
val viewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(PrepViewModel::class.java) ->
                    PrepViewModel(PrepRepositoryImpl())
                isAssignableFrom(PrepCategoryViewModel::class.java) ->
                    PrepCategoryViewModel(PrepCategoryRepositoryImpl())
//                isAssignableFrom(DepartmentViewModel::class.java) ->
//                    DepartmentViewModel()
//                isAssignableFrom(NoticeViewModel::class.java) ->
//                    NoticeViewModel()
//                isAssignableFrom(OrderViewModel::class.java) ->
//                    OrderViewModel()
//                isAssignableFrom(RecipeViewModel::class.java) ->
//                    RecipeViewModel()
//                isAssignableFrom(ScheduleTimeViewModel::class.java) ->
//                    ScheduleTimeViewModel()
//                isAssignableFrom(ScheduleViewModel::class.java) ->
//                    ScheduleViewModel()
//                isAssignableFrom(TeamViewModel::class.java) ->
//                    TeamViewModel()
//                isAssignableFrom(LoginViewModel::class.java) ->
//                    LoginViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}