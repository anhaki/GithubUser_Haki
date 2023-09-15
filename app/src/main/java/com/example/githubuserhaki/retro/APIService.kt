package com.example.githubuserhaki.retro

import com.example.githubuserhaki.response.DetailUsers
import com.example.githubuserhaki.response.FollowResponse
import com.example.githubuserhaki.response.ResponseUsers
import com.example.githubuserhaki.response.UsersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowResponse>>

    @GET("search/users")
    fun searchUser(
        @Query("q")q: String
    ): Call<ResponseUsers>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailUsers>

    @GET("users")
    fun getUsers(): Call<List<UsersItem>>
}