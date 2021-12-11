package com.test.locationapp.di

import com.test.locationapp.service.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiServicesModule {

    @Provides
    fun providesLoginAuthService(retrofit: Retrofit) =
        retrofit.create(LocationService::class.java)
}