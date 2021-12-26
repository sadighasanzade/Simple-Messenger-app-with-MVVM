package com.example.khazarmessenger.model

data class User(
    val userId: String,
    var fullName:String,
    var email:String,

){
    constructor() : this("","","")
}