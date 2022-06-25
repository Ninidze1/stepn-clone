package btu.ninidze.stepcounter.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import btu.ninidze.stepcounter.R

object NotificationUtil {

    const val CHANNEL_ID = "DEFAULT"

    fun showSimpleNotification(context: Context) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_currency)
            .setContentTitle("StepN? is running on background")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())
    }

}