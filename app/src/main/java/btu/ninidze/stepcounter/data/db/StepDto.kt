package btu.ninidze.stepcounter.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StepDto(
    @PrimaryKey
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "steps") val steps: Int
)