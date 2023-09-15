package com.example.githubuserhaki.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserhaki.databinding.ListMainBinding
import com.example.githubuserhaki.response.UsersItem
import com.example.githubuserhaki.ui.DetailActivity

class UsersAdapter(private val listUsers: List<UsersItem>) : RecyclerView.Adapter<UsersAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUsers[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    class ListViewHolder(val binding: ListMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userProp: UsersItem) {
            Glide.with(binding.root.context)
                .load(userProp.avatarUrl)
                .into(binding.ivAvatar)
            binding.tvNama.text = userProp.login
            binding.tvId.text = userProp.id
            binding.ivFav.visibility = View.GONE

            itemView.setOnClickListener {
                val usernameSend = userProp.login
                val avatarSend = userProp.avatarUrl
                var toDetail = Intent(binding.root.context, DetailActivity::class.java)
                toDetail.putExtra(DetailActivity.SEND_LOGIN, usernameSend)
                toDetail.putExtra(DetailActivity.SEND_AVATAR, avatarSend)
                binding.root.context.startActivity(toDetail)
            }
        }
    }
}