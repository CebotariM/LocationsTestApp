package com.test.locationapp.service

import com.test.locationapp.dto.LocationListDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {

    @GET("locations")
    suspend fun getLocationsList(): Response<LocationListDto>
}