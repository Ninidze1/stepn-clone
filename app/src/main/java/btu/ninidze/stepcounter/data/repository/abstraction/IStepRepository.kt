package btu.ninidze.stepcounter.data.repository.abstraction

import btu.ninidze.stepcounter.data.db.StepDto
import kotlinx.coroutines.flow.Flow

interface IStepRepository {

    fun getTotalSteps(): Flow<Int>

    fun getStepsByDay(day: String): Flow<Int>

    suspend fun insert(step: Int)

    suspend fun update(user: StepDto)

    suspend fun deleteAll()
}