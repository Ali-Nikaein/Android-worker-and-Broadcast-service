package com.example.broadcastserviceworker.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder
import com.example.broadcastserviceworker.broadcastreceiver.NetworkChangeReceiver
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkService : Service() {

    private val networkStatus = MutableStateFlow("")

    private val networkReceiver = NetworkChangeReceiver(networkStatus)

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}