package com.spread.androidstudy.studies.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    companion object {
        private var count = 0
    }

    private var _userCount: MutableLiveData<Int> = MutableLiveData()
    val userCount: LiveData<Int> get() = _userCount

    fun addUser() {
        _userCount.postValue(++count)
    }

}