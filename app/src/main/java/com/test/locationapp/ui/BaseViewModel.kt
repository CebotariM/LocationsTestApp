package com.test.locationapp.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.locationapp.util.LiveEvent

abstract class BaseViewModel : ViewModel() {

    private val loadingState = MutableLiveData<Boolean>()
    private val errorEvent = LiveEvent<Int>()

    fun showError(@StringRes errorRes: Int) = errorEvent.postValue(errorRes)
    open fun getErrorEvent() : LiveData<Int> = errorEvent

    fun showLoading() = loadingState.postValue(true)
    fun hideLoading() = loadingState.postValue(false)
    fun getLoadingState(): LiveData<Boolean> = loadingState
}