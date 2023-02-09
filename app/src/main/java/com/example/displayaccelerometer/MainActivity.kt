package com.example.displayaccelerometer


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        sensorManager.registerListener(
            this, accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, myFragment.newInstance())
            commitNow()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]
            val xValue: TextView = findViewById(R.id.x_value)
            val yValue: TextView = findViewById(R.id.y_value)
            val zValue: TextView = findViewById(R.id.z_value)

            xValue.text = x.toString()
            yValue.text = y.toString()
            zValue.text = z.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
            this, accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}