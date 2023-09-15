package com.example.githubuserhaki.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserhaki.response.FollowResponse
import com.example.githubuserhaki.retro.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _listFollow = MutableLiveData<List<FollowResponse>?>()
    val listFollow: LiveData<List<FollowResponse>?> = _listFollow

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    private val _indikasi = MutableLiveData<String>()
    val indikasi: LiveData<String> = _indikasi

    private val _errorText = MutableLiveData<Event<String>>()
    val errorText: MutableLiveData<Event<String>> = _errorText


    fun showFollowers(usernameNya : String, flwNya : Boolean) {
        _loading.value = true
        val client = if(flwNya){
            ApiConfig.getApiService().getFollowers(usernameNya)
        } else {
            ApiConfig.getApiService().getFollowing(usernameNya)
        }

        client.enqueue(object : Callback<List<FollowResponse>> {
            override fun onResponse(call: Call<List<FollowResponse>>, response: Response<List<FollowResponse>>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _loading.value = false
                    _listFollow.value = responseBody
                    if(responseBody.isEmpty()){
                        _empty.value = true
                        if(flwNya) _indikasi.value = "Tidak ada pengikut."
                        else _indikasi.value = "Tidak ada yang diikuti."
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                _loading.value = false
                _errorText.value = Event(if(flwNya) "Gagal mendapatkan data followers" else "Gagal mendapatkan data following")
            }
        })
    }
}