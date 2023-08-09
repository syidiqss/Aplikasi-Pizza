package com.example.pizzaapp.response.cart

data class CartData(
    val id_makanan: String,
    val nama_makanan: String,
    val harga: String,
    val jumlah: String,
    val id_pengguna: String
)