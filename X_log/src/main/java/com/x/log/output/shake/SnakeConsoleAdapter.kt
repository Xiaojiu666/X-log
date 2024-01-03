package com.x.log.output.shake

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import com.x.log.output.ConsoleAdapter
import com.x.log.service.LogMessage

class SnakeConsoleAdapter(override val isLoggable: Boolean, application: Application) :
    ConsoleAdapter {

    init {
        val listener = LogOverlay(LogOverlay.Order.CHRONOLOGICAL_DESCENDING)
        application.registerActivityLifecycleCallbacks(listener)
        initShaking(application, listener)
    }

//    fun showLogMenuWithShake(application: Application, order: LogOverlay.Order) = apply {
//        val listener = LogOverlay(order)
//        application.registerActivityLifecycleCallbacks(listener)
//        initShaking(application, listener)
//    }

    private fun initShaking(
        application: Application,
        shakeListener: ShakeDetector.ShakeListener,
        minShakeCount: Int = 2
    ) {
        val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val shakeDetector = ShakeDetector(minShakeCount)
        shakeDetector.shakeListener = shakeListener
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override suspend fun log(logMsg: LogMessage) {

    }
}