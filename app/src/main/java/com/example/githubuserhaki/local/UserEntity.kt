package com.example.githubuserhaki.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class UserEntity (
    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name = "avatar")
    var avatar: String? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

): Parcelable