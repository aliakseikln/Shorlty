package com.example.task1

import androidx.lifecycle.ViewModel
import com.example.task1.adapter.RecyclerViewAdapter
import com.example.task1.db.repository.ShortlyRepository
import com.example.task1.screens.MainActivity
import com.example.task1.screens.MyViewModel

lateinit var APP: MainActivity
lateinit var REPOSITORY: ShortlyRepository
lateinit var PRESENTER: PresenterImpl
lateinit var VIEWMODEL: MyViewModel
lateinit var RECYCLERVIEWADAPTER : RecyclerViewAdapter