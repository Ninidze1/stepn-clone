package btu.ninidze.stepcounter.data.repository.abstraction

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {

    fun checkIfAlreadyCounted(): Flow<Boolean>

    suspend fun saveCountedOnce()
}