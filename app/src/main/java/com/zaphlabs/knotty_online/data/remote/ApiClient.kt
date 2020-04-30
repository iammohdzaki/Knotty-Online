package com.zaphlabs.knotty_online.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Developer : Mohammad Zaki
 * Created On : 30-04-2020
 */

class ApiClient {
    companion object{
        const val BASE_URL="https://us-central1-jibble-ffd5f.cloudfunctions.net/"
        private var httpClient = OkHttpClient.Builder()
        private var retrofit:Retrofit ?= null

         fun getClient():ApiInterface{
            if(retrofit == null){
                val logging=HttpLoggingInterceptor();
                logging.apply {
                    level=HttpLoggingInterceptor.Level.BODY
                }
                httpClient.addInterceptor(logging)

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }

            return retrofit!!.create(ApiInterface::class.java)
        }
    }

}