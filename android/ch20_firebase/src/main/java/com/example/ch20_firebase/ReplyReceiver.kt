package com.example.ch20_firebase

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.RemoteInput

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //TODO("ReplyReceiver.onReceive() is not implemented")

        //val data = RemoteInput.getDataResultsFromIntent(intent)?.getCharSequence("key_text_reply")
        val data  = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")
        Log.d("25android", "User Text : $data")


        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(11)
    }
}