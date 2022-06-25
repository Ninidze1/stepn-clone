package btu.ninidze.stepcounter.data.sensor

import android.content.Context
import android.content.pm.PackageManager.FEATURE_SENSOR_STEP_COUNTER
import android.hardware.Sensor.TYPE_STEP_COUNTER
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ActivitySensor @Inject constructor(
    @ApplicationContext private val context: Context,
): AndroidSensor(
    context = context,
    sensorFeature = FEATURE_SENSOR_STEP_COUNTER,
    sensorType = TYPE_STEP_COUNTER
)