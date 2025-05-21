package com.example.fcm_client.data

data class Message(
    val title: String,
    val body: String,
    val timestamp: Long = System.currentTimeMillis()
)