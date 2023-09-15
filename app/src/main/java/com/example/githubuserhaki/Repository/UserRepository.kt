package com.example.githubuserhaki.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.githubuserhaki.local.UserDao
import com.example.githubuserhaki.local.UserDatabase
import com.example.githubuserhaki.local.UserEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao : UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.UserDao()
    }

    fun getAllUsers(): LiveData<List<UserEntity>> = mUserDao.getAllUsers()

    fun insert(user: UserEntity) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(username: String) {
        executorService.execute { mUserDao.delete(username) }
    }

    fun selectUser(login: String) : LiveData<Boolean>{
        return mUserDao.getFavoriteUserByUsername(login).map { it.isNotEmpty() }
    }

}