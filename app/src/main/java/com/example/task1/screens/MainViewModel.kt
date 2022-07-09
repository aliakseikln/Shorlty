package com.example.task1.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task1.Contract
import com.example.task1.ModelImpl
import com.example.task1.ServiceListener
import com.example.task1.ViewModelListener
import com.example.task1.db.ShortlyDataBase
import com.example.task1.db.model.ShortlyModel
import com.example.task1.db.repository.ShortlyRealization
import com.example.task1.db.repository.ShortlyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val context: Application, private val service: ModelImpl) :
    AndroidViewModel(context), Contract.ViewModel {

    private lateinit var shortlyRepository: ShortlyRepository

    override fun initDataBase() {
        val daoShortly = ShortlyDataBase.getInstance(context).getShortlyDao()
        shortlyRepository = ShortlyRealization(daoShortly)
    }

    override fun getAllHistoryLinks(): LiveData<List<ShortlyModel>> {
        return shortlyRepository.allShortlyLinks
    }

    override fun insert(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.insertShortly(shortlyModel) {
                onSuccess()
            }
        }

   override fun deleteById(id: Int, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.deleteShortlyById(id) {
                onSuccess()
            }
        }

    override fun delete(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.deleteShortly(shortlyModel) {
                onSuccess()
            }
        }

    override fun update(shortlyModel: ShortlyModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.updateShortly(shortlyModel) {
                onSuccess()
            }
        }

    override fun handleButtonShortenItClick(linkToShorten: String, vmListener: ViewModelListener) {
        service.getResponse(linkToShorten, object : ServiceListener {

            override fun onServiceSuccess(response: ShortlyModel) {
                vmListener.onServiceSuccess(response)
            }

            override fun onFailure(throwable: Throwable) {
                vmListener.onFailure(throwable)
            }

            override fun onIncorrectInputQuery() {
                vmListener.onIncorrectTextQueryInput()
            }

            override fun onItemAlreadyInDB() {
                vmListener.onItemAlreadyInDataBase()
            }
        })
    }
}