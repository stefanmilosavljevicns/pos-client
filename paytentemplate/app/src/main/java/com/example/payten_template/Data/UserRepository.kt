package com.example.payten_template.Data

import androidx.lifecycle.LiveData

class UserRepository(private val todoDatabaseDao: UserDao) {
    val readAllData : LiveData<List<User>> =  todoDatabaseDao.getAll()
    suspend fun addTodo(todoItem: User) {
        todoDatabaseDao.insert(todoItem)
    }
    suspend fun updateTodo(todoItem: User) {
        todoDatabaseDao.update(todoItem)
    }
    suspend fun deleteTodo(todoItem: User) {
        todoDatabaseDao.delete(todoItem)
    }
    suspend fun deleteAllTodos() {
        todoDatabaseDao.deleteAllTodos()
    }
}