package com.example.task1

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task1.screens.MainViewModel

class MainViewModelFactory(
    private val application: Application,
    private val serviceImpl: ServiceImpl
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(context = application, service = serviceImpl) as T
    }
}