package com.example.githubuserhaki.response

import com.google.gson.annotations.SerializedName

data class DetailUsers(
	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("login")
	val login: String? = null,
)
