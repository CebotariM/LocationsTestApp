package com.test.locationapp.repo

import com.test.locationapp.R
import com.test.locationapp.dao.LocationDao
import com.test.locationapp.dto.LocationDto
import com.test.locationapp.service.LocationService
import com.test.locationapp.util.LiveEvent
import javax.inject.Inject

class MainRepository @Inject constructor(private val locationService: LocationService, private val locationDao: LocationDao) : BaseRepository() {

    val errorEvent = LiveEvent<Int>()

    suspend fun getLocationList(): List<LocationDto> {
        val locations = wrapCallInErrorHandling { locationService.getLocationsList() }
        return if (locations is ResultData.Success) {
            locationDao.saveLocations(locations.value?.locations ?: emptyList())
            locations.value?.locations ?: emptyList()
        } else {
            //post http error for error display on UI if necessary
            val errorResult = locations as ResultData.Failure
            errorEvent.postValue(
                when (errorResult.errorCode) {
                    0 -> R.string.general_error
                    else -> R.string.locations_download_error
                }
            )
            locationDao.retrieveListOfLocations()
        }
    }
}