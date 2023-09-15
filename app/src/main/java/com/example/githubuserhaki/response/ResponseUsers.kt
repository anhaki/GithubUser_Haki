package com.example.githubuserhaki.response

import com.google.gson.annotations.SerializedName

data class ResponseUsers(
	@field:SerializedName("items")
	val items: List<UsersItem>
)