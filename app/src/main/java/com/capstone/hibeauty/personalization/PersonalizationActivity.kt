package com.capstone.hibeauty.personalization

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.hibeauty.MainActivity
import com.capstone.hibeauty.databinding.ActivityPersonalizationBinding

class PersonalizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalizationBinding
    private lateinit var adapter: PersonalizationAdapter
    private lateinit var sharedPreferences: SharedPreferences






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PersonalizationAdapter(this)
        binding.viewPager.adapter = adapter
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)


        // Periksa apakah personalisasi sudah selesai
        if (sharedPreferences.getBoolean("isPersonalized", false)) {
            goToMainActivity()
            return
        }
        adapter = PersonalizationAdapter(this)
        binding.viewPager.adapter = adapter

        binding.btnNext.setOnClickListener {
            if (validateCurrentFragment()) {
                if (binding.viewPager.currentItem < adapter.itemCount - 1) {
                    binding.viewPager.currentItem += 1
                } else {
                    saveUserData()
                    goToMainActivity()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateCurrentFragment(): Boolean {
        val currentFragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}")
        return when (currentFragment) {
            is QNameFragment -> currentFragment.isValid()
            is QAgeFragment -> currentFragment.isValid()
            is QGenderFragment -> currentFragment.isValid()
            else -> false
        }
    }

    private fun saveUserData() {
        val nameFragment = adapter.getFragmentAt(0) as QNameFragment
        val ageFragment = adapter.getFragmentAt(1) as QAgeFragment
        val genderFragment = adapter.getFragmentAt(2) as QGenderFragment

        val name = nameFragment.getName()
        val age = ageFragment.getAge()
        val gender = genderFragment.getGender()

        Log.d("PersonalizationActivity", "Nama dari fragment: $name")

        with(sharedPreferences.edit()) {
            putString("name", name)
            putString("age", age)
            putString("gender", gender)
            apply()
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}