package com.example.fcm_client

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fcm_client.data.Message
import com.example.fcm_client.ui.theme.FcmclientTheme
import com.example.fcm_client.ui.theme.components.MessageList
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registrar receptor del mensaje
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {
                val title = intent?.getStringExtra("title") ?: return
                val body = intent.getStringExtra("body") ?: return
                viewModel.addMessage(Message(title, body))
            }
        }
        registerReceiver(receiver, IntentFilter("com.example.fcm_client.MENSAJE_RECIBIDO"))

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "No se pudo obtener el token", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("FCM", "Token del dispositivo: $token")
        }

        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("FCM App")
                            })
                    }
                ) { padding ->
                    MessageList(
                        messages = viewModel.messages,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}
