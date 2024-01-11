package com.example.moniheart.ui.pengukuran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PengukuranViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pengukuran Fragment"
    }
    val text: LiveData<String> = _text
}