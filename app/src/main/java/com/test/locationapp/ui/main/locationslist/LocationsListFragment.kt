package com.test.locationapp.ui.main.locationslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.test.locationapp.databinding.FragmentLocationsListBinding
import com.test.locationapp.dto.LocationDto
import com.test.locationapp.ui.BaseFragment
import com.test.locationapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsListFragment : BaseFragment() {

    private lateinit var binding: FragmentLocationsListBinding
    private lateinit var adapter: LocationsListAdapter

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViewModelListeners()
        setupRefresh()
    }

    private fun setupRefresh() {
        binding.locationsListRefresh.setOnRefreshListener(viewModel)
    }

    private fun setupViewModelListeners() {
        viewModel.getLocationsLiveData().observe(viewLifecycleOwner, { onDataUpdated(it) })
        viewModel.getErrorEvent().observe(viewLifecycleOwner, { showErrorToast(it) })
    }

    private fun onDataUpdated(newData: List<LocationDto>?) {
        adapter.submitList(newData)
        binding.locationsListRefresh.isRefreshing = false
    }

    private fun showErrorToast(res: Int) {
        Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
    }

    private fun setupAdapter() {
        adapter = LocationsListAdapter(viewModel)
        binding.locationsListRecycler.adapter = adapter
    }
}