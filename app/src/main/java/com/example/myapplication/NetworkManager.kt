package com.example.myapplication

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkManager {

    private val api = Retrofit.Builder()
        .baseUrl("https://api.formation-android.fr/")
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory()
        )
        .build()
        .create(API::class.java)

    suspend fun getProduct(barcode: String) : Product? {
        return try {
            val serverResponse = api.getProductByBarcode(barcode).await()
            serverResponse.response?.toProduct()
        } catch (e: Exception) {
            null
        }
    }

}

interface API {

    @GET("getProduct")
    fun getProductByBarcode(@Query("barcode") barcode: String) : Deferred<ServerResponse>
}