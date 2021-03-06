package com.example.todoapp10

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


data class TodoModel(
    var title:String,
    var category: String,
    var date:Long,
    var priority:Int,
    var share:Boolean,
    var id:Long = 0,
    var tag:String
)