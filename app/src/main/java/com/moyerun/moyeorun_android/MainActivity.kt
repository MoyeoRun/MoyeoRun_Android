package com.moyerun.moyeorun_android

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moyerun.moyeorun_android.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import com.moyerun.moyeorun_android.login.ui.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation(binding.bottomNav)
    }

    private fun initBottomNavigation(bottomView: BottomNavigationView) {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_container
        ) as NavHostFragment

        navController = navHostFragment.findNavController()
        bottomView.setupWithNavController(navController)
    }
}