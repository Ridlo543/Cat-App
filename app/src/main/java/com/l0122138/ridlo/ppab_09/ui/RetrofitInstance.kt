package com.l0122138.ridlo.ppab_09.ui

import com.l0122138.ridlo.ppab_09.utils.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object RetrofitInstance {
    private val retrofit by lazy {
        // Membuat interceptor yang menambahkan API key ke header
        val interceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(
                    "x-api-key",
                    "live_CdnRrelR3rT3sn5UCcazTN8ydZ3HcuY2bxhNXn5vcfucFvmZuNzvoRDGa9R9V0Nd"
                )
                .build()
            chain.proceed(newRequest)
        }

        // Membuat OkHttpClient dan menambahkan interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        // Membuat instance Retrofit
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
