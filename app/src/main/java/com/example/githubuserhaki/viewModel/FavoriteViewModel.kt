package com.example.githubuserhaki.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserhaki.Repository.UserRepository
import com.example.githubuserhaki.local.UserEntity

class FavoriteViewModel (application: Application) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val mUserRepository: UserRepository = UserRepository(application)

    fun delete(username: String) {
        mUserRepository.delete(username)
    }

    fun getAllNotes(): LiveData<List<UserEntity>>{
        _loading.value = false
        return mUserRepository.getAllUsers()
    }
}