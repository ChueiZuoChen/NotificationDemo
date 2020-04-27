package com.example.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

/*
*   Notification
*   1. 定義channelID -> 一定是要package的名字才會知道是哪個app觸發了notification
*   2. 創建NotificationManager物件，取得SystemService裡面的Context.NOTIFICATION_SERVICE服務
*   3. 建立NotificationChannel(id, name, importance)
*                   id   -> channelID
*                   name -> channel的名字
*                   importance -> 選擇重要度 NotificationManager.IMPORTANCE_HIGH
* */

class MainActivity : AppCompatActivity() {

    private val channelID = "com.example.notificationdemo.channel1"
    private var notificationManager: NotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "DemoChannel", "This is a demo")
        button.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {

        /*
        * 在notification裡面加入pendingIntent 可以讓使用者點Notification馬上轉跳到第二個畫面
        * */
        val tapResultIntent = Intent(this,SecondActivity::class.java)
        //PendingIntent
        val pendingIntent:PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        //action button 1
        val intent2 = Intent(this,DetailsActivity::class.java)
        val pendingItent2 = PendingIntent.getActivity(
            this,
            0,
            intent2,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val action2=NotificationCompat.Action.Builder(
            0,
            "Details",
            pendingItent2
        ).build()

        //action button 2
        val intent3 = Intent(this,SettingsActivity::class.java)
        val pendingIntent3 = PendingIntent.getActivity(
            this,
            0,
            intent3,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val action3 = NotificationCompat.Action.Builder(
            0,
            "Settings",
            pendingIntent3
        ).build()


        val notificationId = 77
        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) //加入pendingIntent
            .addAction(action2) //加入action -> DetailsActivity
            .addAction(action3) //加入action -> SettingsActivity
            .build()
        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(id: String, name: String, channdelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channdelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}










