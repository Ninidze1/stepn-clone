package btu.ninidze.stepcounter.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import btu.ninidze.stepcounter.data.sensor.MeasurableSensor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class StepWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val sensor: MeasurableSensor) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){

        val previousTotalStep = 0
        var totalSteps = 0f


        sensor.startListening()
        sensor.setOnSensorValuesChangedListener { values ->
            totalSteps = values[0]
            val currentSteps = totalSteps.toInt() - previousTotalStep
            val data = workDataOf("steps" to currentSteps)
            this.launch {
                setProgress(data)
            }
        }

        return@withContext Result.success()

    }

}