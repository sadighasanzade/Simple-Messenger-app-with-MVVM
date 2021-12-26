package com.example.khazarmessenger.viemodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khazarmessenger.repository.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    val registerResult = MutableLiveData<Boolean>()

    fun registerUser(email:String, pass: String,fullName:String, activity: Activity){
        viewModelScope.launch {
            try {
                registerResult.value = repository.registerUser(email, pass,fullName, activity)
            }catch (e:Exception){
                registerResult.value = false
            }
        }


    }
}