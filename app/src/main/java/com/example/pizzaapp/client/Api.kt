package com.example.pizzaapp.client

import com.example.pizzaapp.response.cart.CartData
import com.example.pizzaapp.response.cart.CartResponse
import com.example.pizzaapp.response.login.LoginResponse
import com.example.pizzaapp.response.menu.MenuResponse
import com.example.pizzaapp.response.pembayaran.PembayaranResponse
import com.example.pizzaapp.response.pengguna.PenggunaResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {
    //show (get) menu
    @GET("makanan")
    fun getMenu():Call<ArrayList<MenuResponse>>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
            @Field("username") username:String,
            @Field("password") password:String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("cart")
    fun postCart(
        @Field("id_pengguna") id_pengguna:String
        ): Call<ArrayList<CartData>>

    @FormUrlEncoded
    @POST("pembayaran")
    fun pembayaran(
        @Field("id_pengguna") id_pengguna:String
    ): Call<PembayaranResponse>

    @FormUrlEncoded
    @PUT("cart")
    fun putCart(
        @Field("id_pengguna") id_pengguna:String,
        @Field("id_makanan") id_makanan:String,
        @Field("jumlah") jumlah:Int
    ): Call<CartResponse>

    @FormUrlEncoded
    @PUT("pengguna")
    fun putPengguna(
        @Field("username") username: String,
        @Field("nama") nama:String,
        @Field("level") level:String,
        @Field("password") password: String
    ):Call<PenggunaResponse>
}

