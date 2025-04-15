package com.kitching.app.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kitching.app.common.KitchingApplication
import com.kitching.data.datasource.dataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "team")
class PreferencesDataStore(private val context: Context = KitchingApplication.getInstance()) {
    companion object {
        private val TEAM_ID = stringPreferencesKey("team_id")
        private val TEAM_NAME = stringPreferencesKey("team_name")
        private val USER_ID = stringPreferencesKey("user_id")
    }

    suspend fun saveTeamId(teamId: String) {
        context.dataStore.edit { preferences -> preferences[TEAM_ID] = teamId }
    }

    suspend fun saveTeamName(teamName: String) {
        context.dataStore.edit { preferences -> preferences[TEAM_NAME] = teamName }
    }

    suspend fun getTeamId(): String = context.dataStore.data.first()[TEAM_ID] ?: ""

    suspend fun getTeamName(): String = context.dataStore.data.first()[TEAM_NAME] ?: ""

    suspend fun clearTeamId() {
        context.dataStore.edit { preferences -> preferences.remove(TEAM_ID) }
    }

    suspend fun clearTeamName() {
        context.dataStore.edit { preferences -> preferences.remove(TEAM_NAME) }
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences -> preferences[USER_ID] = userId }
    }

    suspend fun getUserId(): String = context.dataStore.data.first()[USER_ID] ?: ""

    suspend fun clearUserId() {
        context.dataStore.edit { preferences -> preferences.remove(USER_ID) }
    }
}