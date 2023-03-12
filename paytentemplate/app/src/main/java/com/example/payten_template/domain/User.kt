package com.example.payten_template.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "my_todo_list")
data class User(
    @PrimaryKey
    val id: String,
    val pin: Int,
    val nickname: String,
    val name: String,
    val lastName: String,
    val email: String,
    val creationDate: String,
    val location: String?,
    val role: String,
    //val grade: Float
)