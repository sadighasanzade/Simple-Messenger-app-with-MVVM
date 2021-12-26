package com.example.khazarmessenger.viemodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khazarmessenger.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {
    val loginResult = MutableLiveData<Boolean>()
    val isLoggedIn = MutableLiveData<Boolean>()


    fun loginUser(email:String, pass: String, context: Context){
        viewModelScope.launch {
            try {
                loginResult.value = repository.loginUser(email, pass,context)
            }catch (e: Exception){
                loginResult.value = false
            }
        }
    }
    fun checkUser(){
        viewModelScope.launch {
            try{
                isLoggedIn.value = repository.isLoggedIn()
            }catch (e:Exception){
                isLoggedIn.value = false
            }
        }
    }

}