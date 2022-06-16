package com.example.task1

import com.example.task1.model.ShortlyModel

class Contract {

    interface View {

        fun showMainFragment()

        fun showHistoryFragment()

        fun showToastCopiedSuccessfully(textToShow: String)

        fun showToastDeletedSuccessfully()

        fun updateRecyclerViewAdapter(response: ShortlyModel)

        fun showInputErrorMessage()

        fun showToastItemAlreadyInDB()
    }

    interface Presenter {
        fun handleCopyButtonClick(textToCopy: String)

        fun handleDeleteButtonClick(keyOriginalLink: String, keyShortedLink: String)

        fun handleButtonShortenItClick(query: String)
    }

    interface Service {
        fun getResponseBody(urlQuery: String, serviceListener: ServiceListener)
    }
}