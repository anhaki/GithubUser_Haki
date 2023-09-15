package com.example.githubuserhaki.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserhaki.R
import com.example.githubuserhaki.adapter.SectionPagerAdapter
import com.example.githubuserhaki.databinding.ActivityDetailBinding
import com.example.githubuserhaki.helper.ThemeModelFactory
import com.example.githubuserhaki.helper.ViewModelFactory
import com.example.githubuserhaki.local.UserEntity
import com.example.githubuserhaki.response.DetailUsers
import com.example.githubuserhaki.viewModel.DetailViewModel
import com.example.githubuserhaki.viewModel.ThemeViewModel
import com.example.mydatastore.SettingPreferences
import com.example.mydatastore.dataStore
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel

    private var user: UserEntity? = UserEntity()
    private var isFav = false

    private lateinit var theUsername: String
    private lateinit var theAvatar: String

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainViewModel(this@DetailActivity)

        if (savedInstanceState != null) {
            theUsername = savedInstanceState.getString("username", "")
            theAvatar = savedInstanceState.getString("avatar", "")
        } else {
            theUsername = intent.getStringExtra(SEND_LOGIN).toString()
            theAvatar = intent.getStringExtra(SEND_AVATAR).toString()
        }

        detailViewModel.responsebd.observe(this){theResponse ->
            if (theResponse != null) {
                setUserData(theResponse)
                binding.ivFav.visibility = View.VISIBLE
            }
        }

        detailViewModel.loading.observe(this){ theLoading ->
            showLoading(theLoading)
        }

        detailViewModel.errorText.observe(this){
            it.getContentIfNotHandled()?.let {theMsg ->
                Toast.makeText(this@DetailActivity, theMsg, Toast.LENGTH_LONG).show()
                binding.ivFav.visibility = View.GONE
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = intent.getStringExtra(SEND_LOGIN).toString()

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.elevation = 0f

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeModelFactory(pref)).get(
            ThemeViewModel::class.java)

        detailViewModel.selectUser(theUsername).observe(this){apakahFav ->
            if(apakahFav){
                binding.ivFav.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24))
                isFav = true
            } else{
                themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
                    if (isDarkModeActive){
                        binding.ivFav.setImageDrawable(getDrawable(R.drawable.fav_fill_dark))
                    } else{
                        binding.ivFav.setImageDrawable(getDrawable(R.drawable.fav_fill))
                    }
                }
                isFav = false
            }
        }

        binding.ivFav.setOnClickListener {
            if(isFav) {
                detailViewModel.delete(theUsername)
                isFav = false
            } else {
                user?.login = theUsername
                user?.avatar = theAvatar
                detailViewModel.insert(user as UserEntity)
                isFav = true
            }

        }

        if (savedInstanceState == null) {
            detailViewModel.getUser(theUsername)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("username", theUsername)
        outState.putString("avatar", theAvatar)
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun setUserData(theResponseNya: DetailUsers){
        Glide.with(this@DetailActivity)
            .load(theResponseNya.avatarUrl)
            .into(binding.ivAvatar)
        binding.tvLogin.text = theResponseNya.login
        binding.tvName.text = theResponseNya.name
        binding.followersCt.text = theResponseNya.followers.toString()
        binding.followingCt.text = theResponseNya.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.circleImg.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.tvLogin.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.tvName.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.llFollowers.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.llFollowing.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    companion object {
        const val SEND_LOGIN = "SEND_LOGIN"
        const val SEND_AVATAR = "SEND_AVATAR"
        private val TAB_TITLES = arrayOf(
            "Followers",
            "Following",
        )
    }

}
