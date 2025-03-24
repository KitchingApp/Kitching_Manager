package com.kitching.data.exception

import android.util.Log
import com.kitching.domain.entities.FcmToken

/**
 * 커스텀 런타임 예외를 위한 추상 클래스
 *
 */
abstract class KitchingRuntimeException() : RuntimeException() {
    /** 로그 출력용 태그 */
    protected abstract val tag: String

    /** 예외 던지기 전 해야 할 액션 */
    protected abstract fun onThrow()

    /** onThrow 실행 후 예외 리턴 */
    fun getException(): Exception {
        onThrow()
        return this
    }

    /** 로그 출력용 함수 */
    protected fun logInformationBase(printMessage: String) {
        Log.e(tag, printMessage.trimIndent())
    }
}

/** 유저ID로 유저를 찾을 수 없을 때 */
class UserNotFoundException(
    private val userId: String
) : KitchingRuntimeException() {
    override val tag = "USER_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            User Not Found Exception
            Cannot found User(id: $userId)
            """
        )
    }
}

/** 팀ID와 유저ID로 멤버를 찾을 수 없을 때 */
class MemberNotFoundException(
    private val teamId: String,
    private val userId: String
) : KitchingRuntimeException() {
    override val tag = "MEMBER_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Member Not Found Exception
            Cannot found Member(userId: $userId) in Team(id: $teamId)
            """
        )
    }
}

/** 팀ID로 팀을 찾을 수 없을 때 */
class TeamNotFoundException(
    private val teamId: String
) : KitchingRuntimeException() {
    override val tag = "TEAM_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Team Not Found Exception
            Cannot found Team(id: $teamId)
            """
        )
    }
}

/** 레시피ID로 레시피를 찾을 수 없을 때 */
class RecipeNotFoundException(
    private val recipeId: String
) : KitchingRuntimeException() {
    override val tag = "RECIPE_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Recipe Not Found Exception
            Cannot found Recipe(id: $recipeId)
            """
        )
    }
}

/** 스케줄ID로 스케줄을 찾을 수 없을 때 */
class ScheduleNotFoundException(
    private val scheduleId: String
) : KitchingRuntimeException() {
    override val tag = "SCHEDULE_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Schedule Not Found Exception
            Cannot found Schedule(id: $scheduleId)
            """
        )
    }
}

/** 스케줄타임ID로 스케줄타임을 찾을 수 없을 때 */
class ScheduleTimeNotFoundException(
    private val scheduleTimeId: String
) : KitchingRuntimeException() {
    override val tag = "SCHEDULE_TIME_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Schedule Time Not Found Exception
            Cannot found ScheduleTime(id: $scheduleTimeId)
            """
        )
    }
}

/** 스케줄타임ID로 스케줄타임을 찾을 수 없을 때 */
class StaffLevelNotFoundException(
    private val staffLevelId: String
) : KitchingRuntimeException() {
    override val tag = "STAFF_LEVEL_NOT_FOUND_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Staff Level Not Found Exception
            Cannot found StaffLevel(id: $staffLevelId)
            """
        )
    }
}

/** 데이터베이스에 있는 사용자의 모든 FCM 토큰에 푸시 메세지 발송이 실패했을 때 */
class PushMessageFailedException(
    private val userId: String,
    private val failedList: List<Pair<FcmToken, String>>
) : KitchingRuntimeException() {
    override val tag = "FAIELD_SEND_PUSH_MESSAGE_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
            Failed Send Push Message Exception
            cannot send push message to User(id: $userId)
            failedList:
            ${failedList.map { "token: ${it.first.token}, deviceModel: ${it.first.deviceModel}, message: ${it.second}" }}
            """
        )
    }
}

/** Firebase 작업 실패 */
class FailedCRUDInFirebaseException(
    private val throwable: Throwable
): KitchingRuntimeException() {
    override val tag = "FAILED_CRUD_IN_FIREBASE_EXCEPTION"

    override fun onThrow() {
        logInformationBase(
            """
                Failed CRUD In Firebase Exception
                throwable message: ${throwable.message ?: "not found message in throwable"}
            """
        )
    }
}