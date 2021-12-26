package com.example.khazarmessenger.model

class Message (
    val sendId: String,
    var recieverId:String,
    var message:String,

    ){
        constructor() : this("","","")
    }