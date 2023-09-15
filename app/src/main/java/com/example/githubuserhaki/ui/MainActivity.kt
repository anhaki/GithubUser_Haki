package com.example.githubuserhaki.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserhaki.R
import com.example.githubuserhaki.viewModel.MainViewModel
import com.example.githubuserhaki.adapter.UsersAdapter
import com.example.githubuserhaki.databinding.ActivityMainBinding
import com.example.githubuserhaki.helper.ThemeModelFactory
import com.example.githubuserhaki.response.UsersItem
import com.example.githubuserhaki.viewModel.ThemeViewModel
import com.example.mydatastore.SettingPreferences
import com.example.mydatastore.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        mainViewModel.listUser.observe(this) { listUser ->
            if (listUser != null) setUserData(listUser)
        }

        mainViewModel.errorText.observe(this) {
            it.getContentIfNotHandled()?.let { theMsg ->
                Toast.makeText(this@MainActivity, theMsg, Toast.LENGTH_LONG).show()
            }
        }

        mainViewModel.loading.observe(this) { loading ->
            if (loading != null) showLoading(loading)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    var textSearchView = searchView.text.toString().trim()
                    searchBar.text = textSearchView
                    searchView.hide()
                    if (textSearchView == "") {
                        mainViewModel.showUsers()
                    } else mainViewModel.showSearch(textSearchView)
                    true
                }
        }

        //Ganti theme
        val menu1 = binding.searchBar.menu.findItem(R.id.menu1)
        val menu2 = binding.searchBar.menu.findItem(R.id.menu2)
        var isDark: Boolean = false

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            isDark = isDarkModeActive
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                menu1.setIcon(R.drawable.baseline_dark_mode_24)
                menu2.setIcon(R.drawable.baseline_favorite_24_3)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                menu1.setIcon(R.drawable.baseline_light_mode_24)
                menu2.setIcon(R.drawable.baseline_favorite_24_2)
            }
        }

        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    themeViewModel.saveThemeSetting(!isDark)
                    true
                }

                R.id.menu2 -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

    }

    private fun setUserData(userLists: List<UsersItem>) {
        val adapter = UsersAdapter(userLists)
        binding.rvMain.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvMain.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }
}

