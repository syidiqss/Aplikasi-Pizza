package com.example.pizzaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.ColorSpace.Model
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaapp.client.RetrofitClient
import com.example.pizzaapp.response.cart.CartData
import com.example.pizzaapp.response.cart.CartResponse
import com.example.pizzaapp.response.login.LoginResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class CartAdapter(private val list: ArrayList<CartData>) : RecyclerView.Adapter<CartAdapter.MenuViewHolder>(){

    //val data = listOf("aaa","bbbb","cccc","dddd","eeee","ffff","gggg","hhhh")

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_cart, parent, false)

        return MenuViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.bind(list[position])

    }

    inner class MenuViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val textNama:TextView
        val textId:TextView
        val textHarga:TextView
        val textJumlah:TextView
        val textTotal:TextView

        init {
            textNama = v.findViewById(R.id.textNamaMenu)
            textHarga = v.findViewById(R.id.textHargaMenu)
            textJumlah = v.findViewById(R.id.textJumlahMenu)
            textTotal = v.findViewById(R.id.textHargaTotal)
            textId = v.findViewById(R.id.textViewId)
        }

        fun bind(cartData: CartData){
            val id:String = cartData.id_makanan
            val nama:String = cartData.nama_makanan
            val harga:String = cartData.harga
            val jumlah:String = cartData.jumlah
            val total:Int = Integer.parseInt(cartData.harga) * Integer.parseInt(cartData.jumlah)

            textId.text = id
            textNama.text = nama
            textHarga.text = formatingRupiah(harga.toDouble())
            textJumlah.text = jumlah
            textTotal.text = formatingRupiah(total.toString().toDouble())

        }

        private fun formatingRupiah(harga: Double): String? {
            val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
            val formatRp = DecimalFormatSymbols()
            formatRp.currencySymbol = "Rp. "
            formatRp.monetaryDecimalSeparator = ','
            formatRp.groupingSeparator = '.'
            kursIndonesia.decimalFormatSymbols = formatRp
            return kursIndonesia.format(harga)
        }
    }


}