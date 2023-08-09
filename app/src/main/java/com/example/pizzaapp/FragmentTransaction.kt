package com.example.pizzaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaapp.client.RetrofitClient
import com.example.pizzaapp.response.cart.CartData
import com.example.pizzaapp.response.cart.CartResponse
import com.example.pizzaapp.response.menu.MenuResponse
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTransaction.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTransaction : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val list = ArrayList<CartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        val rvmenu: RecyclerView = view.findViewById(R.id.recyclerMenu)
        val grandTotal: TextView = view.findViewById(R.id.grandTotal)
        val buttonBayar: MaterialButton = view.findViewById(R.id.buttonBayar)
        val pembayaran = resources.getStringArray(R.array.pembayaran)
        val spinner = view.findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            val adapter = ArrayAdapter(view.context,
                android.R.layout.simple_spinner_item, pembayaran)
            spinner.adapter = adapter
        }

        rvmenu.apply {
            rvmenu.layoutManager = GridLayoutManager(activity,1)

            var session: Session?
            session = Session(view.context)

            RetrofitClient.instance.postCart(
                session.email!!
            ).enqueue(object : Callback<ArrayList<CartData>> {
                override fun onResponse(
                    call: Call<ArrayList<CartData>>,
                    response: Response<ArrayList<CartData>>
                ) {
                    list.clear()
                    response.body()?.let { list.addAll(it) }
                    var adapter = CartAdapter(list)
                    rvmenu.adapter = adapter

                    var a = 0
                    list.forEach {
                        a += (Integer.parseInt(it.harga) * Integer.parseInt(it.jumlah))
                    }
                    grandTotal.text = formatingRupiah(a.toString().toDouble())
                }

                override fun onFailure(call: Call<ArrayList<CartData>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

        buttonBayar.setOnClickListener {
            val intentLogin = Intent(view.context, PembayaranActivity::class.java)
            startActivity(intentLogin)
        }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTransaction.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTransaction().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}