package com.example.task1

import com.example.task1.db.model.ShortlyModel

interface ViewModelListener {
    fun onServiceSuccess(response: ShortlyModel)

    fun onFailure(throwable: Throwable)

    fun onIncorrectTextQueryInput()

    fun onItemAlreadyInDataBase()
}