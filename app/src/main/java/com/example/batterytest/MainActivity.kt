package com.example.batterytest

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var batteryReceiver: BatteryReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        batteryReceiver = BatteryReceiver()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, intentFilter)

        val percentageInput = findViewById<EditText>(R.id.percentageInput)
        val setPercentageButton = findViewById<Button>(R.id.setPercentageButton)
        val stopRingtoneButton = findViewById<Button>(R.id.stopRingtoneButton)

        setPercentageButton.setOnClickListener {
            val percentage = percentageInput.text.toString().toIntOrNull()
            if (percentage != null) {
                val sharedPreferences = getSharedPreferences("BatteryAlarm", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putInt("battery_percentage", percentage)
                    apply()
                }
            }
        }

        stopRingtoneButton.setOnClickListener {
            batteryReceiver.stopRingtone()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }
}
