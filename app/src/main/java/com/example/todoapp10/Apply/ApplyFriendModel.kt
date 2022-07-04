package com.example.todoapp10.Apply

import androidx.room.Entity
import androidx.room.PrimaryKey


data class ApplyFriendModel(
    var id:Long = 0,
    var name:String,
    var pass:String
)