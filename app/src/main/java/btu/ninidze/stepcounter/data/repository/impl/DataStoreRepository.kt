package btu.ninidze.stepcounter.data.repository.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import btu.ninidze.stepcounter.data.repository.abstraction.IDataStoreRepository
import btu.ninidze.stepcounter.data.repository.impl.DataStoreRepository.KEYS.ALREADY_COUNTED
import btu.ninidze.stepcounter.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.DATA_SORE, produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, Constants.DATA_STORE_PREF))
    }
)

@Singleton
class DataStoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>):
    IDataStoreRepository {

    override fun checkIfAlreadyCounted(): Flow<Boolean> = dataStore.data.map { value ->
        value[ALREADY_COUNTED] ?: false
    }

    override suspend fun saveCountedOnce() {
        dataStore.edit { value ->
            value[ALREADY_COUNTED] = true
        }
    }

    private object KEYS {
        val ALREADY_COUNTED = booleanPreferencesKey("counted")
    }
}