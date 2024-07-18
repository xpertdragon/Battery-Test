package com.example.batterytest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.BatteryManager

class BatteryReceiver : BroadcastReceiver() {

    companion object {
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = level / scale.toFloat() * 100

            val sharedPreferences = context.getSharedPreferences("BatteryAlarm", Context.MODE_PRIVATE)
            val targetPercentage = sharedPreferences.getInt("battery_percentage", 80)

            if (batteryPct == targetPercentage.toFloat()) {
                playRingtone(context)
            }
        }
    }

    private fun playRingtone(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ringtone)
        }
        mediaPlayer?.start()
    }

    fun stopRingtone() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
