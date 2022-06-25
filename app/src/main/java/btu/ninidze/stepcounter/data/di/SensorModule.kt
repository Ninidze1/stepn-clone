package btu.ninidze.stepcounter.data.di

import android.app.Application
import btu.ninidze.stepcounter.data.sensor.ActivitySensor
import btu.ninidze.stepcounter.data.sensor.MeasurableSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SensorModule {

    @Provides
    @Singleton
    fun provideLightSensor(app: Application): MeasurableSensor = ActivitySensor(app)
}