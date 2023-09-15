package com.example.githubuserhaki.response

import com.google.gson.annotations.SerializedName

data class UsersItem(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,
	)