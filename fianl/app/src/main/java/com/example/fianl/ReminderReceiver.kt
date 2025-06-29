package com.example.fianl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val builder = NotificationCompat.Builder(context, "record_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("오늘의 응원")
            .setContentText("오늘도 힘내세요! 💪 건강 기록도 잊지 마세요.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(101, builder.build())
        }
    }
}
