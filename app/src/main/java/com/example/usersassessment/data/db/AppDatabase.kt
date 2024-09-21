package com.example.usersassessment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usersassessment.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}