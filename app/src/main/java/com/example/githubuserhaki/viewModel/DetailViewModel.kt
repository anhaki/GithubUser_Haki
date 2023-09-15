package com.example.githubuserhaki.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserhaki.Repository.UserRepository
import com.example.githubuserhaki.local.UserEntity
import com.example.githubuserhaki.response.DetailUsers
import com.example.githubuserhaki.retro.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _responseBd = MutableLiveData<DetailUsers?>()
    val responsebd: MutableLiveData<DetailUsers?> = _responseBd

    private val _errorText = MutableLiveData<Event<String>>()
    val errorText: MutableLiveData<Event<String>> = _errorText

    fun insert(note: UserEntity) {
        mUserRepository.insert(note)
    }

    fun selectUser(login: String): LiveData<Boolean> {
        return mUserRepository.selectUser(login)
    }

    fun delete(username: String) {
        mUserRepository.delete(username)
    }

    fun getUser(usernameDetail: String) {
        val client = ApiConfig.getApiService().getDetail(usernameDetail)
        _loading.value = true
        client.enqueue(object : Callback<DetailUsers> {
            override fun onResponse(
                call: Call<DetailUsers>,
                response: Response<DetailUsers>
            ) {
                val responseBody = response.body()
                _loading.value = false
                if (response.isSuccessful && responseBody != null) {
                    _responseBd.value = responseBody
                }
            }

            override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                _loading.value = false
                _errorText.value = Event("Gagal mendapatkan detail user")
            }
        })
    }
}
