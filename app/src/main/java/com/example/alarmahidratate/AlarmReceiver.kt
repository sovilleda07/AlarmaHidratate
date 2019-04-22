package com.example.alarmahidratate

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context!!)

    }

    fun showNotification(context:Context) {
        val i = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(context, 0, i, 0)
        val mBuilder = NotificationCompat.Builder(context)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Hora del Agua")
            .setContentText("Tienes que tomar agua ahora. Es por salud.")
        mBuilder.setContentIntent(pi)
        mBuilder.setDefaults(Notification.DEFAULT_SOUND)
        mBuilder.setAutoCancel(true)
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(0, mBuilder.build())
    }


}