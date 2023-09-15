package com.example.githubuserhaki.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserhaki.databinding.ListMainBinding
import com.example.githubuserhaki.helper.UserDiffCallback
import com.example.githubuserhaki.local.UserEntity
import com.example.githubuserhaki.ui.DetailActivity
import com.example.githubuserhaki.viewModel.FavoriteViewModel
import java.util.ArrayList

class FavoriteAdapter(val favViewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {
    private val listFav = ArrayList<UserEntity>()
    fun setListFav(listFav: List<UserEntity>) {
        val diffCallback = UserDiffCallback(this.listFav, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFav.clear()
        this.listFav.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ListMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    override fun getItemCount(): Int {
        return listFav.size
    }

    inner class FavViewHolder(private val binding: ListMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: UserEntity) {
            with(binding) {
                tvNama.text = fav.login

                Glide.with(binding.root.context)
                    .load(fav.avatar)
                    .into(binding.ivAvatar)
                binding.tvNama.text = fav.login
                binding.tvId.visibility = View.GONE

                binding.ivFav.setOnClickListener {
                    favViewModel.delete(fav.login.toString())
                }

                itemView.setOnClickListener {
                    val usernameSend = fav.login
                    val avatarSend = fav.avatar
                    var toDetail = Intent(binding.root.context, DetailActivity::class.java)
                    toDetail.putExtra(DetailActivity.SEND_LOGIN, usernameSend)
                    toDetail.putExtra(DetailActivity.SEND_AVATAR, avatarSend)
                    binding.root.context.startActivity(toDetail)
                }
            }
        }
    }
}