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
import com.example.pizzaapp.response.cart.CartResponse
import com.example.pizzaapp.response.login.LoginResponse
import com.example.pizzaapp.response.menu.MenuResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuAdapter(private val list: ArrayList<MenuResponse>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    //val data = listOf("aaa","bbbb","cccc","dddd","eeee","ffff","gggg","hhhh")

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_menu, parent, false)

        return MenuViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.bind(list[position])

    }

    inner class MenuViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val textNama:TextView
        val textId:TextView
        val textHarga:TextView
        val imgMenu:ImageView
        val buttonTambah: Button

        init {
            textNama = v.findViewById(R.id.textNamaMenu)
            textId = v.findViewById(R.id.textViewId)
            textHarga = v.findViewById(R.id.textHargaMenu)
            imgMenu = v.findViewById(R.id.imageMenu)
            buttonTambah = v.findViewById(R.id.buttonTambah)
            buttonTambah.setOnClickListener {
                var session: Session?
                session = Session(v.context)

                if (session.email != null) {
                    RetrofitClient.instance.putCart(
                        session.email!!,textId.text.toString().trim(), 1
                    ).enqueue(object : Callback<CartResponse>{
                        override fun onResponse(
                            call: Call<CartResponse>,
                            response: Response<CartResponse>
                        ) {
                            val user = response.body()
                            Toast.makeText(v.context, user!!.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                            Toast.makeText(v.context, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }

        fun bind(menuResponse: MenuResponse){
            val id:String = "${menuResponse.id_makanan}"
            val nama:String = "${menuResponse.nama_makanan}"
            val harga:String = "${menuResponse.harga}"
            val gambar:String = "${menuResponse.gambar}"

            textId.text = id
            textNama.text = nama
            textHarga.text = harga

            //val bmImg = BitmapFactory.decodeFile(gambar)
            //imgMenu.setImageBitmap(bmImg)

            //Picasso.get().load("http://192.168.100.10:70/rest_api/foto/American%20Beef.png").into(imgMenu)
            Picasso.get().load(gambar).into(imgMenu)
        }
    }


}