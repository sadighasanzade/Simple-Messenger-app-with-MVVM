package com.example.khazarmessenger.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.khazarmessenger.controller.NetworkOperations
import com.example.khazarmessenger.controller.onDataListener
import com.example.khazarmessenger.controller.onMessageUpdate
import com.example.khazarmessenger.model.Message
import com.example.khazarmessenger.model.User

class Repository {
    val controller = NetworkOperations()

    suspend fun registerUser(email: String, password: String, fullName: String, activity: Activity): Boolean{
        return controller.registerUser(email,password,fullName,activity )
    }
    suspend fun loginUser(email: String, password: String, context: Context): Boolean{
        return controller.loginUser(email,password,context)
    }

    suspend fun isLoggedIn() :Boolean{
        return controller.isLoggedIn()
    }

    suspend fun logOut(){
        controller.logOutUser()
    }

    suspend fun getUsers(listener: onDataListener){
        controller.getAllUsers(listener)

    }
    suspend fun getUid(): String{
        return controller.getCurrentUserId()
    }

    suspend fun sendMessage(message: Message, listener: onMessageUpdate) {
        controller.sendMessage(message, listener)

    }

    fun getAllMessages(room: String, listener: onMessageUpdate) {
        controller.updateData(listener,room)

    }
}