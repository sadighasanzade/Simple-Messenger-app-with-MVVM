package com.example.khazarmessenger.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khazarmessenger.controller.onDataListener
import com.example.khazarmessenger.model.User
import com.example.khazarmessenger.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    var uid : MutableLiveData<String> = MutableLiveData()
    fun logOut(){
        viewModelScope.launch {
            repository.logOut()
        }
    }

    fun getAllUsers(listener: onDataListener){
        viewModelScope.launch {
            repository.getUsers(listener)
        }
    }
    fun getUid(){
        viewModelScope.launch {
            uid.value = repository.getUid()
        }
    }
}