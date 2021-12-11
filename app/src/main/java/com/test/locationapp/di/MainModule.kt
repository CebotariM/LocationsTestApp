package com.test.locationapp.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.test.locationapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val REALM_VERSION = 1L

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .callTimeout(20L, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesChuckInterceptor(context: Application) = ChuckerInterceptor.Builder(context).build()

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun providesRealmConfig(): RealmConfiguration =
        RealmConfiguration.Builder()
            .schemaVersion(REALM_VERSION)
            .build()

}