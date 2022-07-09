package com.example.task1

import androidx.lifecycle.LiveData
import com.example.task1.db.model.ShortlyModel
import kotlinx.coroutines.Job

class Contract {

    interface View {

        fun showToastLinkAlreadyInHistory()

        fun showMessageInputError()
    }

    interface ViewModel {

        fun initDataBase()

        fun getAllHistoryLinks(): LiveData<List<ShortlyModel>>

        fun insert(shortlyModel: ShortlyModel, onSuccess: () -> Unit): Job

        fun delete(shortlyModel: ShortlyModel, onSuccess: () -> Unit): Job

        fun deleteById(id: Int, onSuccess: () -> Unit): Job

        fun update(shortlyModel: ShortlyModel, onSuccess: () -> Unit): Job

        fun handleButtonShortenItClick(linkToShorten: String, vmListener: ViewModelListener)
    }

    interface Model {
        fun getResponse(urlQuery: String, serviceListener: ServiceListener)
    }
}