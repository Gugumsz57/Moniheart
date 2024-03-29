package com.example.moniheart.app

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    object ApiConfig {
        private const val BASE_URL = "http://192.168.56.1:8080/moniheart/backup/moniheart/public/api/"
        private val client: Retrofit
            get() {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()

                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
            }

        val instanceRetrofit: ApiService
            get() = client.create(ApiService::class.java)
    }


}