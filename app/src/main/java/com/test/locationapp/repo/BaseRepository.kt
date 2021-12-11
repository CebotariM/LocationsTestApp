package com.test.locationapp.repo

import retrofit2.Response
import java.net.HttpURLConnection

abstract class BaseRepository {

    protected suspend fun <T> wrapCallInErrorHandling(networkCall: suspend () -> Response<T>): ResultData<T?> {
        return try {
            val response = networkCall.invoke()
            when (response.code()) {
                HttpURLConnection.HTTP_ACCEPTED,
                HttpURLConnection.HTTP_CREATED,
                HttpURLConnection.HTTP_NO_CONTENT,
                HttpURLConnection.HTTP_OK -> ResultData.Success(response.body())
                else -> ResultData.Failure(response.code(), response.message())
            }
        } catch (t: Throwable) {
            ResultData.Failure(0, t.message)
        }
    }
}

sealed class ResultData<out T> {

    data class Success<out T>(val value: T) : ResultData<T>()

    data class Failure<out T>(val errorCode: Int, val message: String? = null) : ResultData<T>()
}