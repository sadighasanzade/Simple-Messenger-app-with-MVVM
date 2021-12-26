package com.example.khazarmessenger.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.khazarmessenger.repository.Repository

class ChatViewModelFactory(private val repo: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(repo) as T

    }
}