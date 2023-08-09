package com.example.pizzaapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.pizzaapp.client.RetrofitClient
import com.example.pizzaapp.response.login.LoginResponse
import com.example.pizzaapp.response.pembayaran.PembayaranResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private var i = 0
    private var txtView: TextView? = null
    private val handler = Handler()

    companion object {
        var session: Session? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: PembayaranActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        session = Session(applicationContext)

        //hide title bar
        supportActionBar?.hide()

        //instance button next
        progressBar = findViewById(R.id.progressBar)
        txtView = findViewById(R.id.text)

        progressBar!!.visibility = View.VISIBLE

        i = progressBar!!.progress

        RetrofitClient.instance.pembayaran(
            session!!.email!!
        ).enqueue(object : Callback<PembayaranResponse> {
            override fun onResponse(
                call: Call<PembayaranResponse>,
                response: Response<PembayaranResponse>
            ) {
                //val user = response.body()
            }

            override fun onFailure(call: Call<PembayaranResponse>, t: Throwable) {
                //Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        Thread {
            // this loop will run until the value of i becomes 99
            while (i < 100) {
                i += 1
                // Update the progress bar and display the current value
                handler.post {
                    progressBar!!.progress = i
                    // setting current progress to the textview
                    if(i == 99){
                        Toast.makeText(this@PembayaranActivity, "Pembayaran Berhasil", Toast.LENGTH_SHORT).show()
                        //Toast.makeText(applicationContext, "Pembayaran Berhasil", Toast.LENGTH_SHORT).show()
                    }
                }
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            progressBar!!.visibility = View.INVISIBLE

            val intentLogin = Intent(this@PembayaranActivity, MainActivity::class.java)
            startActivity(intentLogin)
        }.start()


    }
}