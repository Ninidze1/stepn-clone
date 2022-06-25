package btu.ninidze.stepcounter.data.repository.impl

import btu.ninidze.stepcounter.data.db.StepDao
import btu.ninidze.stepcounter.data.db.StepDto
import btu.ninidze.stepcounter.data.repository.abstraction.IStepRepository
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StepRepository @Inject constructor(private val db: StepDao): IStepRepository {

    override fun getTotalSteps(): Flow<Int> = db.getTotalSteps()

    override fun getStepsByDay(day: String): Flow<Int> = db.getStepsByDay(day)

    override suspend fun insert(step: Int) {
        db.insert(
            StepDto(
                day = SimpleDateFormat("dd/M/yyyy", Locale.US).format(Date()),
                steps = step
            )
        )
    }

    override suspend fun update(user: StepDto) {
        db.update(user)
    }

    override suspend fun deleteAll() {
        db.deleteAll()
    }
}