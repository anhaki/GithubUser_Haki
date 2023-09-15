package com.example.githubuserhaki.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao

    companion object{
        @Volatile
        private var instance: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            if (instance == null) {
                synchronized(UserDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "user_database")
                        .build()
                }
            }
            return instance as UserDatabase
        }
    }
}