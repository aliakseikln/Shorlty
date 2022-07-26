package com.example.task1.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task1.ServiceImpl
import com.example.task1.ServiceListener
import com.example.task1.ViewModelListener
import com.example.task1.db.ShortlyDataBase
import com.example.task1.db.repository.ShortlyRealization
import com.example.task1.db.repository.ShortlyRepository
import com.example.task1.model.ShortlyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val context: Application, val service: ServiceImpl) :
    AndroidViewModel(context) {

    private lateinit var shortlyRepository: ShortlyRepository

    fun initDataBase() {
        val daoShortly = ShortlyDataBase.getInstance(context).getShortlyDao()
        shortlyRepository = ShortlyRealization(daoShortly)
    }

    fun getAllHistoryLinks(): LiveData<List<ShortlyModel>> {
        return shortlyRepository.allShortlyLinks
    }

    fun delete(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.deleteShortly(shortlyModel) {
                onSuccess()
            }
        }

    fun insert(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.insertShortly(shortlyModel) {
                onSuccess()
            }
        }

    fun handleButtonShortenItClick(linkToShorten: String, vmListener: ViewModelListener) {
        service.getResponseBody(linkToShorten, object : ServiceListener {

            override fun onServiceSuccess(response: ShortlyModel) {
                vmListener.onServiceSuccess(response)
            }

            override fun onFailure(throwable: Throwable) {
                vmListener.onFailure(throwable)
            }

            override fun onIncorrectInputQuery() {
                vmListener.onIncorrectInputQuery()
            }

            override fun onItemAlreadyInDB() {
                vmListener.onItemAlreadyInDB()
            }
        })
    }
}