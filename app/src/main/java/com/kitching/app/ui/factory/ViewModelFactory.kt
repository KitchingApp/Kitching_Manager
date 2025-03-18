package com.kitching.app.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitching.app.common.KitchingApplication
import com.kitching.app.ui.model.InviteCodeViewModel
import com.kitching.app.ui.model.LoginViewModel
import com.kitching.app.ui.model.MemberViewModel
import com.kitching.app.ui.model.NoticeViewModel
import com.kitching.app.ui.model.OrderCategoryViewModel
import com.kitching.app.ui.model.OrderViewModel
import com.kitching.app.ui.model.PrepCategoryViewModel
import com.kitching.app.ui.model.PrepViewModel
import com.kitching.app.ui.model.RecipeViewModel
import com.kitching.app.ui.model.ScheduleTimeViewModel
import com.kitching.app.ui.model.ScheduleViewModel
import com.kitching.app.util.PreferencesDataStore
import com.kitching.app.ui.model.StaffLevelViewModel
import com.kitching.app.ui.model.TeamViewModel
import com.kitching.data.repository.LoginRepositoryImpl
import com.kitching.data.repository.NoticeRepositoryImpl
import com.kitching.data.repository.OrderCategoryRepositoryImpl
import com.kitching.data.repository.OrderRepositoryImpl
import com.kitching.data.repository.PrepCategoryRepositoryImpl
import com.kitching.data.repository.PrepRepositoryImpl
import com.kitching.data.repository.PushMessageRepositoryImpl
import com.kitching.data.repository.RecipeRepositoryImpl
import com.kitching.data.repository.ScheduleRepositoryImpl
import com.kitching.data.repository.ScheduleTimeRepositoryImpl
import com.kitching.data.repository.StaffLevelRepositoryImpl
import com.kitching.data.repository.TeamRepositoryImpl
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
                        scheduleRepository = ScheduleRepositoryImpl(),
                        pushMessageRepository = PushMessageRepositoryImpl()
                    )
                isAssignableFrom(NoticeViewModel::class.java) ->
                    NoticeViewModel(
                        noticeRepository = NoticeRepositoryImpl()
                    )
                isAssignableFrom(StaffLevelViewModel::class.java) ->
                    StaffLevelViewModel(
                        repository = StaffLevelRepositoryImpl()
                    )
                isAssignableFrom(ScheduleTimeViewModel::class.java) ->
                    ScheduleTimeViewModel(
                        repository = ScheduleTimeRepositoryImpl()
                    )
                isAssignableFrom(MemberViewModel::class.java) ->
                    MemberViewModel(
                        userTeamRepository = UserTeamRepositoryImpl(),
                        staffLevelRepository = StaffLevelRepositoryImpl()
                    )
                isAssignableFrom(OrderViewModel::class.java) ->
                    OrderViewModel(
                        orderRepository = OrderRepositoryImpl()
                    )
                isAssignableFrom(OrderCategoryViewModel::class.java) ->
                    OrderCategoryViewModel(
                        orderCategoryRepository = OrderCategoryRepositoryImpl()
                    )
                isAssignableFrom(InviteCodeViewModel::class.java) ->
                    InviteCodeViewModel(
                        teamRepository = TeamRepositoryImpl()
                    )
                isAssignableFrom(RecipeViewModel::class.java) ->
                    RecipeViewModel(
                        recipeRepository = RecipeRepositoryImpl(),
                        dataStore = PreferencesDataStore(context = KitchingApplication.getInstance())
                    )
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(
                        loginRepository = LoginRepositoryImpl(),
                        teamRepository = TeamRepositoryImpl(),
                        dataStore = PreferencesDataStore(context = KitchingApplication.getInstance())
                    )
                isAssignableFrom(TeamViewModel::class.java) ->
                    TeamViewModel(
                        teamRepository = TeamRepositoryImpl(),
                        dataStore = PreferencesDataStore(context = KitchingApplication.getInstance())
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}