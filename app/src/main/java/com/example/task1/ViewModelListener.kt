package com.example.task1

import com.example.task1.data.pojo.Shortly

interface ViewModelListener {

    fun onServiceSuccess(response: Shortly)

    fun onFailure(throwable: Throwable)

    fun onIncorrectTextQueryInput()

    fun onItemAlreadyInDataBase()
}