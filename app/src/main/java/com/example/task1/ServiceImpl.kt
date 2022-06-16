package com.example.task1

import android.util.Log
import com.example.task1.model.ShortlyModel
import retrofit2.Call
import retrofit2.Response


class ServiceImpl : Contract.Service {

    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface: ApiInterface? = retrofit.create(ApiInterface::class.java)

    override fun getResponseBody(urlQuery: String, serviceListener: ServiceListener) {
        try {
            val response = apiInterface?.getResponseByUrlQuery(urlQuery)
            response?.enqueue(object : retrofit2.Callback<ModelBodyResponse?> {
                override fun onResponse(
                    call: Call<ModelBodyResponse?>,
                    response: Response<ModelBodyResponse?>
                ) {
                    if (response.body() != null) {
                        println(response.body().toString())
                        val originalLink = response.body()!!.result.original_link
                        val shortedLink = response.body()!!.result.short_link
                        val shortlyModel = putLinksInSharedPreferences(originalLink, shortedLink)

                        serviceListener.onServiceSuccess(shortlyModel)
                    } else {
                        serviceListener.onIncorrectInputQuery()
                    }
                }

                override fun onFailure(call: Call<ModelBodyResponse?>, t: Throwable) {
                    serviceListener.onFailure(t)
                }
            })
        } catch (Ex: Exception) {
            Ex.localizedMessage?.let { Log.e("Error", it) }
        }


    }

    fun putLinksInSharedPreferences(originalLink: String, shortedLink: String): ShortlyModel {
        val shortlyModel = ShortlyModel()
        shortlyModel.originalLink = originalLink
        shortlyModel.shortlyLink = shortedLink
        return shortlyModel
    }

    fun deleteLinksFromSharedPreferences() {
    }
}