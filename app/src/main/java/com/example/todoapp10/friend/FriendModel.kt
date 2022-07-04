package com.example.todoapp10.friend

import androidx.room.Entity
import androidx.room.PrimaryKey


data class FriendModel(
    var id:Long = 0,
    var name:String,
    var pass:String
)