package com.example.task1.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task1.REPOSITORY
import com.example.task1.db.ShortlyDataBase
import com.example.task1.db.repository.ShortlyRealization
import com.example.task1.model.ShortlyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    fun initDataBase() {
        val daoShortly = ShortlyDataBase.getInstance(context).getShortlyDao()
        REPOSITORY = ShortlyRealization(daoShortly)
    }

    fun getAllShortly(): LiveData<List<ShortlyModel>> {
        return REPOSITORY.allShortly
    }

    fun delete(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteShortly(shortlyModel) {
                onSuccess()
            }
        }

    fun update(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.updateShortly(shortlyModel) {
                onSuccess()
            }
        }

    fun insert(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertShortly(shortlyModel) {
                onSuccess()
            }
        }
}