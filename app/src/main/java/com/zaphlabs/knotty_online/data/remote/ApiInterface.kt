package com.zaphlabs.knotty_online.data.remote

import com.zaphlabs.knotty_online.data.model.Event
import com.zaphlabs.knotty_online.data.model.User
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Developer : Mohammad Zaki
 * Created On : 30-04-2020
 */

interface ApiInterface {

    @GET("getEvents")
    fun getEvents(): Call<List<Event>>


}