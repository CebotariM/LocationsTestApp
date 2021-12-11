package com.test.locationapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.test.locationapp.R
import com.test.locationapp.databinding.ActivityMainBinding
import com.test.locationapp.dto.LocationDto
import com.test.locationapp.ui.main.locationdetails.LocationDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        addViewModelListeners()
    }

    private fun addViewModelListeners() {
        viewModel.getOnLocationSelectedEvent().observe(this, { openDetailsFragment(it) })
    }

    private fun openDetailsFragment(locationDto: LocationDto) {
        val transaction =
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.main_container, LocationDetailsFragment.getDetailsFragmentFor(locationDto), null)
                .addToBackStack(LocationDetailsFragment::class.java.name)
        transaction.commit()
    }

}