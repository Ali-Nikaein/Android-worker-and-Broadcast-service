package com.example.broadcastserviceworker.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.broadcastserviceworker.util.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkChangeReceiver(private val networkStatus: MutableStateFlow<String>) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        val status = if (isConnected) {
            "Connected"
        } else {
            "Disconnected"
        }

        networkStatus.value = status
        NotificationHelper.showNotification(context, status)
    }
}