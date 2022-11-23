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
    lateinit var currencyItemSelected : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dateTV = findViewById(R.id.dateView)
        resultTV = findViewById(R.id.resultView)
        convertBtn = findViewById(R.id.convertBtn)
        inputNum = findViewById(R.id.inputUser)


        //Dropdown List ---------------------------------------------------
        // access the items of the list
        val currencyItem = resources.getStringArray(R.array.Currencies)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, currencyItem)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    currencyItemSelected = currencyItem[position].toString()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        //Response API data
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

                    if (num.isNotEmpty()) {

                        //Add your code with dropdown list here ------------------
                        when (currencyItemSelected) {

                            "ada" -> {
                                resultTV.text = ("Result: ${num.toFloat() * currencies[0]}")
                                dateTV.text = date
                            }
                            "aed" -> {
                                resultTV.text = ("Result: ${num.toFloat() * currencies[1]}")
                                dateTV.text = date
                            }
                            "egp" -> {
                                resultTV.text = ("Result: ${num.toFloat() * currencies[2]}")
                                dateTV.text = date
                            }
                            "afn" -> {
                                resultTV.text = ("Result: ${num.toFloat() * currencies[3]}")
                                dateTV.text = date
                            }
                            "amp" -> {
                                resultTV.text = ("Result: ${num.toFloat() * currencies[4]}")
                                dateTV.text = date
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Please enter the amount", Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                Log.d("retrofit", "onFailure: ${t.message.toString()}")
            }

        })

    }

}