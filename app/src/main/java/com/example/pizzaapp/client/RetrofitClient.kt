package com.example.pizzaapp.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    //const val BASE_URL = "http://192.168.100.10:70/rest_api/index.php/"
    //const val BASE_URL = "http://localhost/rest_api/index.php/"
    const val BASE_URL = "http://192.168.43.22/rest_api/index.php/"

    val instance:Api by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(Api::class.java)
    }
}
























