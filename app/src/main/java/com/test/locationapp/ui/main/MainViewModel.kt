package com.test.locationapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.locationapp.dto.LocationDto
import com.test.locationapp.repo.MainRepository
import com.test.locationapp.ui.BaseViewModel
import com.test.locationapp.util.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel(), SwipeRefreshLayout.OnRefreshListener {

    private val locationsMutableLiveData = MutableLiveData<List<LocationDto>>()
    private val onLocationSelectedEvent = LiveEvent<LocationDto>()

    init {
        getLocationList()
    }

    fun getLocationsLiveData(): LiveData<List<LocationDto>> = locationsMutableLiveData
    fun getOnLocationSelectedEvent(): LiveData<LocationDto> = onLocationSelectedEvent
    override fun getErrorEvent() : LiveData<Int> = mainRepository.errorEvent

    private fun getLocationList() {
        showLoading()
        viewModelScope.launch {
            locationsMutableLiveData.postValue(mainRepository.getLocationList())
            hideLoading()
        }
    }

    override fun onRefresh() {
        getLocationList()
    }

    fun onLocationSelected(item: LocationDto) {
        onLocationSelectedEvent.value = item
    }
}