package com.example.task1

import android.util.Log
import com.example.task1.model.ShortlyModel
import com.example.task1.screens.MainActivity

class PresenterImpl(private val view: MainActivity, private val service: ServiceImpl) : Contract.Presenter {

    override fun handleCopyButtonClick(textToCopy: String) {
        view.showToastCopiedSuccessfully(textToCopy)
    }

    override fun handleDeleteButtonClick(keyOriginalLink: String, keyShortedLink: String) {
        view.showToastDeletedSuccessfully()
      //  service.deleteLinksFromSharedPreferences(keyOriginalLink, keyShortedLink)
    }

    override fun handleButtonShortenItClick(query: String) {
        service.getResponseBody(query, object : ServiceListener {

            override fun onServiceSuccess(response: ShortlyModel) {
                view.updateRecyclerViewAdapter(response)
                view.showHistoryFragment()
            }

            override fun onFailure(throwable: Throwable) {
                throwable.localizedMessage?.let { Log.e("PENA", it) }
            }

            override fun onIncorrectInputQuery() {
                view.showInputErrorMessage()
            }

            override fun onItemAlreadyInDB() {
                view.showToastItemAlreadyInDB()
                view.showHistoryFragment()
            }
        })
    }
}
