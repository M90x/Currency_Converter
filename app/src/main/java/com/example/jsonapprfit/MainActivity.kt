package com.example.jsonapprfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var convertBtn : Button
    private lateinit var dateTV : TextView
    private lateinit var resultTV : TextView
    private lateinit var inputNum : EditText

    var currencies = arrayListOf<Float>()
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dateTV = findViewById(R.id.dateView)
        resultTV = findViewById(R.id.resultView)
        convertBtn = findViewById(R.id.convertBtn)
        inputNum = findViewById(R.id.inputUser)


        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getCurrency()?.enqueue(object : Callback<Currency> {
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {

                currencies.add(response.body()!!.eur.ada.toFloat())
                currencies.add(response.body()!!.eur.aed.toFloat())
                currencies.add(response.body()!!.eur.egp.toFloat())
                currencies.add(response.body()!!.eur.afn.toFloat())
                currencies.add(response.body()!!.eur.amp.toFloat())
                date = response.body()!!.date


                convertBtn.setOnClickListener {

                    var num = inputNum.text.toString()
                    var result = num.toFloat() * currencies[1]

                    resultTV.text = ("Result: $result")

                    dateTV.text = date

                }

            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                Log.d("retrofit", "onFailure: ${t.message.toString()}")
            }

        })

    }

}