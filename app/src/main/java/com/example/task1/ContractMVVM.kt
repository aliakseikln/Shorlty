package com.example.task1

import androidx.lifecycle.LiveData
import com.example.task1.data.pojo.Shortly
import kotlinx.coroutines.Job

class ContractMVVM {

    interface View {
        fun showMessageInputError()
    }

    interface ViewModel {

        fun initDataBase()

        fun getAllHistoryLinks(): LiveData<List<Shortly>>

        fun insert(shortly: Shortly): Job

        fun delete(shortly: Shortly): Job

        fun deleteById(id: Int): Job

        fun update(shortly: Shortly): Job

        fun handleButtonShortenItClick(linkToShorten: String, vmListener: ViewModelListener)
    }

    interface Model {
        fun getResponse(urlQuery: String, modelListener: ModelListener)
    }
}