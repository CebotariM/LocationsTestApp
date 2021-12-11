package com.test.locationapp.ui.main.locationdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.locationapp.databinding.FragmentLocationDetailsBinding
import com.test.locationapp.dto.LocationDto
import com.test.locationapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private var location: LocationDto? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        location = requireArguments().getParcelable(LOCATION_KEY)
        binding = FragmentLocationDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.location = location
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLocationImage()
    }

    private fun setupLocationImage() {
        Glide.with(binding.root.context).load(location?.image).into(binding.locationDetailsPhoto)
    }

    companion object {

        private const val LOCATION_KEY = "LOCATION_KEY"

        fun getDetailsFragmentFor(locationDto: LocationDto): LocationDetailsFragment {
            val fragment = LocationDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(LOCATION_KEY, locationDto)
            fragment.arguments = bundle
            return fragment
        }
    }
}