package com.example.khazarmessenger.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khazarmessenger.controller.onMessageUpdate
import com.example.khazarmessenger.model.Message
import com.example.khazarmessenger.repository.Repository
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: Repository) : ViewModel() {
    var uid : MutableLiveData<String> = MutableLiveData()
    fun getUid(){
        viewModelScope.launch {
            uid.value = repository.getUid()
        }
    }

    fun sendMessage(message: Message, listener: onMessageUpdate) {
        viewModelScope.launch {
            repository.sendMessage(message, listener)
        }
    }

    fun getAllMessages(room: String, listener: onMessageUpdate) {
        viewModelScope.launch {
            repository.getAllMessages(room, listener)
        }
    }

}