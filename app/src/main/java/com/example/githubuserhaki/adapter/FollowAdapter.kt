package com.example.githubuserhaki.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserhaki.databinding.ListFollowBinding
import com.example.githubuserhaki.response.FollowResponse


class FollowAdapter(private val listFollowers: List<FollowResponse>) : RecyclerView.Adapter<FollowAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFollowers.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listFollowers[position]
        holder.bind(user)
    }

    class ListViewHolder(private val binding: ListFollowBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(followersProp: FollowResponse) {
            Glide.with(binding.root.context)
                .load(followersProp.avatarUrl)
                .into(binding.ivAvatar)
            binding.tvNama.text = followersProp.login
        }
    }

}