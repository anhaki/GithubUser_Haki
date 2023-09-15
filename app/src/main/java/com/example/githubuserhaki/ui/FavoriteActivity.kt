package com.example.githubuserhaki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserhaki.adapter.FavoriteAdapter
import com.example.githubuserhaki.databinding.ActivityFavoriteBinding
import com.example.githubuserhaki.helper.ViewModelFactory
import com.example.githubuserhaki.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private var _activityFavBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavBinding

    private lateinit var adapter: FavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val favViewModel = obtainViewModel(this@FavoriteActivity)
        favViewModel.getAllNotes().observe(this){ favList ->
            if (favList != null) {
                adapter.setListFav(favList)
                binding?.noFav?.visibility = if(favList.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        favViewModel.loading.observe(this){loading ->
            if (loading != null) showLoading(loading)
        }

        adapter = FavoriteAdapter(favViewModel)

        binding?.rvFav?.layoutManager = LinearLayoutManager(this)
        binding?.rvFav?.setHasFixedSize(true)
        binding?.rvFav?.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding?.rvFav?.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavBinding = null
    }

}