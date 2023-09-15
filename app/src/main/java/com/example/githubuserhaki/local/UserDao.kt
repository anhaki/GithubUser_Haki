package com.example.githubuserhaki.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user : UserEntity)

    @Query("DELETE FROM users WHERE login = :username")
    fun delete(username: String)

    @Query("SELECT * FROM users WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<List<UserEntity>>
}