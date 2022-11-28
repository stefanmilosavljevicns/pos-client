package com.example.payten_template.Data


import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT pin from my_todo_list WHERE nickname LIKE :nickname ")
    fun checkPin(nickname: String): kotlin.Int

    @Query("SELECT nickname from my_todo_list")
    fun getAll(): List<String>

    @Insert
    suspend fun insert(item: User)

    @Update
    suspend fun update(item: User)

    @Delete
    suspend fun delete(item: User)

    @Query("DELETE FROM my_todo_list")
    suspend fun deleteAllTodos()
}