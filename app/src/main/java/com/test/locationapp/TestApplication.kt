package com.test.locationapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}