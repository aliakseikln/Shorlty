package com.example.task1.data.db

import android.util.Log
import com.example.task1.ContractMVVM
import com.example.task1.ModelListener
import com.example.task1.data.pojo.ModelBodyResponse
import com.example.task1.data.pojo.Shortly
import com.example.task1.data.retrofit.ApiInterface
import com.example.task1.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response


class ModelImpl : ContractMVVM.Model {

    private var copiedItemId: Int = 0
    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface: ApiInterface? = retrofit.create(ApiInterface::class.java)

    fun setCopiedItemId(copiedItemId: Int){
        this.copiedItemId = copiedItemId
    }

    fun getCopiedItemId(): Int {
        return copiedItemId
    }

    override fun getResponse(urlQuery: String, modelListener: ModelListener) {
        try {
            val response = apiInterface?.getResponseByUrlQuery(urlQuery)
            response?.enqueue(object : retrofit2.Callback<ModelBodyResponse?> {
                override fun onResponse(
                    call: Call<ModelBodyResponse?>,
                    response: Response<ModelBodyResponse?>
                ) {
                    if (response.body() != null) {
                        val originalLink = response.body()!!.result.original_link
                        val shortedLink = response.body()!!.result.short_link
                        val shortly = Shortly(
                            originalLink = originalLink,
                            shortlyLink = shortedLink
                        )

                        modelListener.onServiceSuccess(shortly)
                    } else {
                        modelListener.onIncorrectInputQuery()
                    }
                }

                override fun onFailure(call: Call<ModelBodyResponse?>, t: Throwable) {
                    modelListener.onFailure(t)
                }
            })
        } catch (Ex: Exception) {
            Ex.localizedMessage?.let { Log.e("Error", it) }
        }
    }
}