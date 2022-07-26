package com.example.task1

import com.example.task1.data.pojo.Shortly

interface ModelListener {

    fun onServiceSuccess(response: Shortly)

    fun onFailure(throwable: Throwable)

    fun onIncorrectInputQuery()

    fun onItemAlreadyInDB()
}