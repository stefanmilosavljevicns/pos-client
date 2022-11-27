package com.example.payten_template.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User ::class], version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun todoDao(): UserDao
    companion object {
        private var INSTANCE: UserDB? = null
        fun getInstance(context: Context): UserDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        "todo_list_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}