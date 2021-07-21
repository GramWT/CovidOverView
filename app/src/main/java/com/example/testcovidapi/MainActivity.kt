package com.example.testcovidapi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testcovidapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.listContainer, CovidDataListFragment.createFragment())
            addToBackStack(null)
            commit()
        }

        binding.backButton.setOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }

    fun setTitle() {
        when (supportFragmentManager.findFragmentById(R.id.listContainer)) {
            is CovidDataListFragment -> {
                binding.titleTextView.text = getString(R.string.covid_data)
                binding.backButton.visibility = View.GONE
            }
            is CovidDetailsFragment -> {
                binding.titleTextView.text = getString(R.string.country_details)
                binding.backButton.visibility = View.VISIBLE
            }
        }
    }



}