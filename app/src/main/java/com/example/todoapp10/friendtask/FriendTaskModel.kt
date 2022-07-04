package com.example.todoapp10.friendtask

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


data class FriendTaskModel(
    var id:Long = 0,
    var userName:String,
    var title:String,
    var category: String,
    var date:Long,
    var priority:Int,
    var share:Boolean,
    var tag:String
)