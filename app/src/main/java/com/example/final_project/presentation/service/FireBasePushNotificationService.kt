package com.example.final_project.presentation.service

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log.d
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.final_project.R
import com.example.final_project.presentation.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireBasePushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        d("onMessageReceived", "${message.data}")
        val data = message.data
        showNotification(
            id = data["id"] ?: "1",
            title = data["title"] ?: "",
            description = data["description"] ?: ""
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun showNotification(id: String, title: String, description: String) {
        val bundle = Bundle().apply {
            putInt("id", id.toInt())
        }

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.productDetailedFragment)
            .setArguments(bundle)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(id.toInt(), notification)
        }

    }

}
