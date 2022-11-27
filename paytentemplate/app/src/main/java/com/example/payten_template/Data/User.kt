package com.example.payten_template.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_todo_list")
data class User (

    @PrimaryKey
    @ColumnInfo(name = "user_name")
    val itemName: String,

    @ColumnInfo(name = "pin")
    var pin: Integer
)