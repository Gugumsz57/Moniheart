package com.example.moniheart.app

import retrofit2.Call
import com.example.moniheart.model.ResponModel
import com.example.moniheart.model.ResponModelRiwayat
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("registrasi")
    fun register(
        @Field("nama") nama :String,
        @Field("email") email :String,
        @Field("jk") jk :String,
        @Field("umur") umur :String,
        @Field("alamat") alamat :String,
        @Field("password") password :String,
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email :String,
        @Field("password") password :String,
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("riwayat")
    fun riwayat(
        @Field("user_id") user_id :String,
        @Field("bpm") bpm :String,
        @Field("kondisi") kondisi :String,
        @Field("waktu") waktu :String,
        @Field("catatan") catatan :String,

    ): Call<ResponModelRiwayat>

    @GET("riwayat/user/{id}")
    fun getriwayat(
        @Path("id") id: Int

    ): Call<ResponModelRiwayat>


}