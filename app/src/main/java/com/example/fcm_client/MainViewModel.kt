package com.example.fcm_client

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fcm_client.data.Message

class MainViewModel : ViewModel() {

    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    fun addMessage(message: Message) {
        _messages.add(0, message) // Se agrega al inicio para ver el más reciente arriba
    }
}
