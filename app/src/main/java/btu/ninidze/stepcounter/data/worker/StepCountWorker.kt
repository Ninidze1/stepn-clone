package btu.ninidze.stepcounter.data.worker

import android.content.Context
import androidx.work.*
import btu.ninidze.stepcounter.util.NotificationUtil
import java.util.concurrent.TimeUnit

class StepCountWorker(context: Context, private val params: WorkerParameters): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val inputData = params.inputData.getInt("steps", 0) + 200
        val inputDataModified = Data.Builder().putInt("steps", inputData).build()

        val imitateSteps = OneTimeWorkRequestBuilder<StepCountWorker>()
            .setInputData(inputDataModified)
            .setInitialDelay(2, TimeUnit.SECONDS)
            .addTag("TAG_OUTPUT")
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(imitateSteps)

        val data = workDataOf("steps" to inputData)
        setProgress(data)
        return Result.success(data)

    }

}