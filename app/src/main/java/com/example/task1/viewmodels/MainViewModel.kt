package com.example.task1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task1.ContractMVVM
import com.example.task1.data.db.ModelImpl
import com.example.task1.ModelListener
import com.example.task1.ViewModelListener
import com.example.task1.data.db.ShortlyDataBase
import com.example.task1.data.pojo.Shortly
import com.example.task1.data.repository.ShortlyRealization
import com.example.task1.data.repository.ShortlyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val context: Application, private val service: ModelImpl) :
    AndroidViewModel(context), ContractMVVM.ViewModel {

    private lateinit var shortlyRepository: ShortlyRepository

    override fun initDataBase() {
        val daoShortly = ShortlyDataBase.getInstance(context).getShortlyDao()
        shortlyRepository = ShortlyRealization(daoShortly)
    }

    override fun getAllHistoryLinks(): LiveData<List<Shortly>> {
        return shortlyRepository.allShortlyLinks
    }

    override fun insert(shortly: Shortly) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.insertShortly(shortly)
        }

    override fun deleteById(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.deleteShortlyById(id)
        }

    override fun delete(shortly: Shortly) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.deleteShortly(shortly)
        }

    override fun update(shortly: Shortly) =
        viewModelScope.launch(Dispatchers.IO) {
            shortlyRepository.updateShortly(shortly)
        }

    override fun handleButtonShortenItClick(linkToShorten: String, vmListener: ViewModelListener) {
        service.getResponse(linkToShorten, object : ModelListener {

            override fun onServiceSuccess(response: Shortly) {
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

    fun setCopiedItemId(copiedItemId: Int){
        service.setCopiedItemId(copiedItemId)
    }

    fun getCopiedItemId(): Int {
        return service.getCopiedItemId()
    }
}