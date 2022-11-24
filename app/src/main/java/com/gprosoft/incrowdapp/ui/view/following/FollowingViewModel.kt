package com.gprosoft.incrowdapp.ui.view.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FollowingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is following Fragment"
    }
    val text: LiveData<String> = _text
}