package com.x.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    fun clickLog() {
        XLog.d("MyViewModel msg")
        viewModelScope.launch {
            XLog.e("viewModelScope launch")
        }

        viewModelScope.launch {
            launch {
                XLog.e("Tread ${Thread.currentThread().name}")
            }
        }
    }
}