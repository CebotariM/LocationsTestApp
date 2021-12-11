package com.test.locationapp.dao

import com.test.locationapp.dto.LocationDto
import com.test.locationapp.dto.LocationRealmObject
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject

class LocationDao @Inject constructor(private val realmConfiguration: RealmConfiguration) {

    suspend fun saveLocation(locationDto: LocationDto) {
        val realm = Realm.getInstance(realmConfiguration)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.insert(convertDtoToRealmObject(locationDto))
        }
    }

    suspend fun saveLocations(locationsDto: List<LocationDto>) {
        val realm = Realm.getInstance(realmConfiguration)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.insert(locationsDto.map { convertDtoToRealmObject(it) })
        }
    }

    suspend fun retrieveListOfLocations(): List<LocationDto> {
        val realm = Realm.getInstance(realmConfiguration)
        val realmLocations = mutableListOf<LocationDto>()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmLocations.addAll(realmTransaction
                .where(LocationRealmObject::class.java)
                .findAll()
                .map { convertRealmObjectToDto(it) }
            )
        }
        return realmLocations
    }

    private fun convertDtoToRealmObject(locationDto: LocationDto) =
        LocationRealmObject().apply {
            id = UUID.randomUUID().toString()
            lat = locationDto.lat
            lng = locationDto.lng
            label = locationDto.label
            address = locationDto.address
            image = locationDto.image
        }

    private fun convertRealmObjectToDto(locationRealmObject: LocationRealmObject) =
        LocationDto(locationRealmObject.lat, locationRealmObject.lng, locationRealmObject.label, locationRealmObject.address, locationRealmObject.image)
}