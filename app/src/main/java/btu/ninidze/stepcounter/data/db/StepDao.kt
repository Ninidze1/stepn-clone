package btu.ninidze.stepcounter.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {

    @Query("SELECT sum(steps) FROM stepdto")
    fun getTotalSteps(): Flow<Int>

    @Query("SELECT steps FROM stepdto WHERE day = :day")
    fun getStepsByDay(day: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: StepDto)

    @Update
    suspend fun update(user: StepDto)

    @Query("DELETE FROM stepdto")
    suspend fun deleteAll()
}