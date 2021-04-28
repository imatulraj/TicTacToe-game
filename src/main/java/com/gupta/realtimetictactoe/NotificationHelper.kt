package com.gupta.realtimetictactoe

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class NotificationHelper(context:Context):ContextWrapper(context) {
    val manager:NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    init{
        val reset=NotificationChannel(RESET,"Reset all ", NotificationManager.IMPORTANCE_DEFAULT)
            reset.lightColor= Color.GREEN
        reset.description="user is saying to reset your content"
            reset.lockscreenVisibility=Notification.VISIBILITY_PRIVATE
        manager.createNotificationChannel(reset)
        val request=NotificationChannel(REQUEST,"TICTACTOE? ", NotificationManager.IMPORTANCE_DEFAULT)
        request.lightColor= Color.GREEN
        request.description="showing when useer send request to play"
        request.lockscreenVisibility=Notification.VISIBILITY_PRIVATE
        manager.createNotificationChannel(request)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationReset(title:String, Body:String):Notification.Builder{
        return Notification.Builder(applicationContext, RESET)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(Body)
    }
    fun notificationRequest(title:String,Body:String):Notification.Builder{
        return Notification.Builder(applicationContext, REQUEST)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(Body)
    }
    fun notify(id:Int,Notifica:Notification.Builder){
        manager.notify(id,Notifica.build())
    }
    companion object{
        val RESET="reset"
        val REQUEST="request"
    }
}