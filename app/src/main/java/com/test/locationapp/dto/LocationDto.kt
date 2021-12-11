package com.test.locationapp.dto

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class LocationDto(
    val lat: Float,
    val lng: Float,
    val label: String? = null,
    val address: String? = null,
    val image: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(lat)
        parcel.writeFloat(lng)
        parcel.writeString(label)
        parcel.writeString(address)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationDto> {
        override fun createFromParcel(parcel: Parcel): LocationDto {
            return LocationDto(parcel)
        }

        override fun newArray(size: Int): Array<LocationDto?> {
            return arrayOfNulls(size)
        }
    }
}

open class LocationRealmObject : RealmObject() {
    @PrimaryKey
    lateinit var id: String
    var lat: Float = 0f
    var lng: Float = 0f
    var label: String? = null
    var address: String? = null
    var image: String? = null
}