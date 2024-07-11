package com.example.broadcastserviceworker
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.example.broadcastserviceworker.broadcastreceiver.NetworkChangeReceiver
import com.example.broadcastserviceworker.ui.theme.NotificationScreen
import com.example.broadcastserviceworker.worker.scheduleBluetoothAirplaneWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val networkStatus = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkReceiver = NetworkChangeReceiver(networkStatus)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)

        setContent {
            NotificationScreen(networkStatus)
        }

        lifecycleScope.launch {
            networkStatus.collectLatest { status ->
                // Handle status change if needed
            }
        }

        // Schedule the BluetoothAirplaneWorker
        scheduleBluetoothAirplaneWorker(this)
    }
}