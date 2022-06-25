package btu.ninidze.stepcounter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StepDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): StepDao
}