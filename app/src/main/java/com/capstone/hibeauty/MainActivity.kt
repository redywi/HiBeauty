package com.capstone.hibeauty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.capstone.hibeauty.article.ArticleFragment
import com.capstone.hibeauty.dashboard.HomeFragment
import com.capstone.hibeauty.product.ProductFragment
import com.capstone.hibeauty.profile.ProfileFragment
import com.capstone.hibeauty.scan.CameraActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var floatButton: FloatingActionButton  // Tambahkan FAB ke sini


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.nav_view)
        floatButton = findViewById(R.id.floating_button)  // Referensi ke FAB

        // Setup listener untuk FAB
        floatButton.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))  // Buka ScanActivity
        }

        // Gunakan OnNavigationItemSelectedListener untuk Bottom Navigation
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    replace(HomeFragment())
                    true
                }
                R.id.product -> {
                    replace(ProductFragment())
                    true
                }
                R.id.news -> {
                    replace(ArticleFragment())
                    true
                }
                R.id.profile -> {
                    replace(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // Inisialisasi dengan fragment default (misalnya, HomeFragment)
        replace(HomeFragment())
    }

    private fun replace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navhost, fragment)
        fragmentTransaction.commit()
    }
}