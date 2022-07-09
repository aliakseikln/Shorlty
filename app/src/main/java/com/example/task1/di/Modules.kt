package com.example.task1.di

import android.app.Application
import com.example.task1.ModelImpl
import com.example.task1.screens.HistoryActivity
import com.example.task1.screens.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val screensModule = module {

    single {
        HistoryActivity()
    }

    single<ModelImpl> {
        ModelImpl()
    }

    single<Application> {
        androidContext() as Application
    }
}

val appModule: Module = module {

    viewModel<MainViewModel> {
        MainViewModel(context = get(), service = get())
    }
}