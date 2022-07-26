package com.example.task1

class Contract {

    interface View {

        fun showMainFragment()

        fun showHistoryFragment()

        fun showToastCopiedSuccessfully(textToCopy: String)

        fun showToastDeletedSuccessfully()

        fun showToastLinkAlreadyInHistory()

        fun showMessageInputError()
    }

    interface Service {
        fun getResponseBody(urlQuery: String, serviceListener: ServiceListener)
    }
}