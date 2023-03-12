package com.example.payten_template.repositories

import com.example.payten_template.domain.User
import com.example.payten_template.domain.UserDao


class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User) {
        userDao.insert(user)
    }

    suspend fun checkPin(pin: String) {
        userDao.checkPin(pin)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}