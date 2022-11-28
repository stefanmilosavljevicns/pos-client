package com.example.payten_template.Data


class UserRepository(private val todoDatabaseDao: UserDao) {


    suspend fun addTodo(todoItem: User) {
        todoDatabaseDao.insert(todoItem)
    }

    suspend fun checkPin(todoItem: String) {
        todoDatabaseDao.checkPin(todoItem)
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