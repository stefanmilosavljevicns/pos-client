package com.example.payten_template.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface  UserDao {
    @Query("SELECT * from my_todo_list")
    fun getAll(): LiveData<List<User>>
    @Insert
    suspend fun insert(item:User)
    @Update
    suspend fun update(item:User)
    @Delete
    suspend fun delete(item:User)
    @Query("DELETE FROM my_todo_list")
    suspend fun deleteAllTodos()
}