package com.example.crickethub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class homepage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val bnv: BottomNavigationView = findViewById(R.id.bnv)
        val  navView : BottomNavigationView=findViewById(R.id.bnv)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame) as NavHostFragment
        val navController : NavController = navHostFragment.navController

        navView.setupWithNavController(navController)
    }
}