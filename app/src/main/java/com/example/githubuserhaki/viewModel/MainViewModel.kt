package com.example.githubuserhaki.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserhaki.response.ResponseUsers
import com.example.githubuserhaki.response.UsersItem
import com.example.githubuserhaki.retro.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel () : ViewModel() {
    private val _listUser = MutableLiveData<List<UsersItem>?>()
    val listUser: LiveData<List<UsersItem>?> = _listUser

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _errorText = MutableLiveData<Event<String>>()
    val errorText: MutableLiveData<Event<String>> = _errorText

    init{
        showUsers()
    }


    fun showUsers() {
        var client = ApiConfig.getApiService().getUsers()
        _loading.value = true
        client.enqueue(object : Callback<List<UsersItem>> {
            override fun onResponse(call: Call<List<UsersItem>>, response: Response<List<UsersItem>>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _loading.value = false
                    _listUser.value = responseBody
                }
            }
            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                _loading.value = false
                _errorText.value = Event("Gagal mendapatkan data-data user")
            }
        })
    }

    fun showSearch(search: String) {
        var client = ApiConfig.getApiService().searchUser(search)
        _loading.value = true
        client.enqueue(object : Callback<ResponseUsers> { // Change to List<UsersItem>
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _loading.value = false
                    _listUser.value = responseBody.items
                    if (responseBody.items.isEmpty()){
                        _errorText.value = Event("User tidak ada")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                _loading.value = false
                _errorText.value = Event("Gagal mendapatkan data-data user yang dicari")
            }
        })
    }



}