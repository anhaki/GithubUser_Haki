package com.example.githubuserhaki.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserhaki.local.UserEntity

class UserDiffCallback(private val oldNoteList: List<UserEntity>, private val newNoteList: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.login == newNote.login && oldNote.avatar == newNote.avatar
    }
}
