package com.example.mybinder.Funciones

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Adapters.ProbHandAdapter
import com.example.mybinder.Adapters.ProbMainAdapter
import com.example.mybinder.Model.Carta
import com.example.mybinder.Model.Mazo
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper
import com.example.mybinder.controllers.OnItemClickListener

class Probabilidad_Activity : AppCompatActivity() {

    lateinit var mazoAdapter: ProbMainAdapter
    lateinit var manoAdapter: ProbHandAdapter
    var mainDeck = mutableListOf<Carta>()
    var nombre: String = ""
    val mazo = Mazo(0, nombre)
    var cantidad = ""
    val mano = mutableListOf<Carta>()
    var manoCantidad = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.probabilidad_layout)

        val imageViewBG = findViewById<ImageView>(R.id.imageViewBG)

        Glide.with(this)
            .asGif()
            .load(R.raw.fondo_gif)
            .into(object : SimpleTarget<GifDrawable>() {
                override fun onResourceReady(
                    resource: GifDrawable,
                    transition: Transition<in GifDrawable>?
                ) {
                    imageViewBG.setImageDrawable(resource)
                    resource.start()
                }
            })

        val idRec = intent.getIntExtra("id", 0)
        val nombreRec = intent.getStringExtra("nombre")

        val nombreDeck = findViewById<TextView>(R.id.deckName)
        val numeroDeck = findViewById<TextView>(R.id.numeroCardDeck)
        val deck = findViewById<RecyclerView>(R.id.mainDeckPR)
        val spinner = findViewById<Spinner>(R.id.spinnerCartaMano)
        val pro1 = findViewById<TextView>(R.id.prob1)
        val pro2 = findViewById<TextView>(R.id.prob2)
        val pro3 = findViewById<TextView>(R.id.prob3)
        val pro4 = findViewById<TextView>(R.id.prob4)
        val pro5 = findViewById<TextView>(R.id.prob5)
        val pro6 = findViewById<TextView>(R.id.prob6)
        val pro6Text = findViewById<TextView>(R.id.card6)
        val manoR = findViewById<RecyclerView>(R.id.recyclerProb)
        val clean_btn = findViewById<Button>(R.id.clean_btn)

        pro1.text = "0%"
        pro2.text = "0%"
        pro3.text = "0%"
        pro4.text = "0%"
        pro5.text = "0%"
        pro6.text = "0%"


        nombreDeck.text = nombreRec

        val databaseHelper = DatabaseHelper(this)

        val mazoFull = databaseHelper.obtenerImagenesIntermedia(databaseHelper.readableDatabase, idRec)

        mainDeck = databaseHelper.obtenerImagenesIntermedia(databaseHelper.readableDatabase, idRec)
        numeroDeck.text = mainDeck.size.toString()

        var mazoCantidad = mainDeck.size


        //Adapter del mazo
        val layoutM = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        deck.layoutManager = layoutM
        mazoAdapter = ProbMainAdapter(mainDeck, object : OnItemClickListener {
            override fun onItemClick(position: Int) {

                var numCop = cuentaCopias(mainDeck[position].imagen)
                var enMano = 0

                for(cartaH in mano){
                    if( cartaH.imagen == mainDeck[position].imagen)
                        enMano++
                }

                if(mano.size  < cantidad.toInt()){

                    val carta = mainDeck[position]
                    mano.add(carta)
                    manoAdapter.notifyDataSetChanged()

                    mainDeck.removeAt(position)
                    mazoAdapter.notifyDataSetChanged()

                    var conta = 0

                    for (cartaH in mano){
                        if(conta == 0){
                            conta++
                        }
                        else {
                            if(conta < mano.size){
                                if (cartaH == carta) {
                                    numCop--
                                    conta++
                                }
                                else{
                                    conta++
                                }
                            }
                        }
                    }

                   var porcentaje = probabilidadCartaEnMano(mazoCantidad,numCop,manoCantidad)

                    mazoCantidad--
                    manoCantidad--

                    for(cartaH in mano)
                    {
                        if(cartaH == carta) {

                            if (cartaH == mano[0]) {
                                pro1.text = (porcentaje * 100).toString() + "%"
                            } else if (cartaH == mano[1]) {
                                pro2.text = (porcentaje * 100).toString() + "%"
                            } else if (cartaH == mano[2]) {
                                pro3.text = (porcentaje * 100).toString() + "%"
                            } else if (cartaH == mano[3]) {
                                pro4.text = (porcentaje * 100).toString() + "%"
                            } else if (cartaH == mano[4]) {
                                pro5.text = (porcentaje * 100).toString() + "%"
                            } else {
                                pro6.text = (porcentaje * 100).toString() + "%"
                            }
                        }
                    }
                }
            }
        })
        deck.adapter = mazoAdapter

        //Adapter mano
        val layoutH = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        manoR.layoutManager = layoutH
        manoAdapter = ProbHandAdapter(mano, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })
        manoR.adapter = manoAdapter


        //Spinner cantidad
        val listaCant = resources.getStringArray(R.array.cantidad)
        val cantidadAdapter = ArrayAdapter(this, R.layout.spinner_layout, listaCant)
        spinner.adapter = cantidadAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCant = parent?.getItemAtPosition(position) as String
                cantidad = selectedCant
                mazoAdapter.notifyDataSetChanged()

                if(cantidad == "5"){
                    pro6.text = ""
                    pro6Text.text = ""
                    manoCantidad
                    if(mano.size == 6) {
                        mano.removeAt(5)
                        manoAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    manoCantidad =6
                    pro6.text = "0%"
                    pro6Text.text = "Carta 6:"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        clean_btn.setOnClickListener{

            if(cantidad == "5"){
                manoCantidad = 5
                pro6.text = ""
                pro6Text.text = ""
            }
            else{
                manoCantidad = 6
                pro6.text = "0%"
            }

            mazoCantidad =  mainDeck.size
            pro1.text = "0%"
            pro2.text = "0%"
            pro3.text = "0%"
            pro4.text = "0%"
            pro5.text = "0%"

            mainDeck.clear()
            mainDeck.addAll(mazoFull)
            mazoAdapter.notifyDataSetChanged()

            mano.clear()
            manoAdapter.notifyDataSetChanged()


        }


    }

    fun cuentaCopias(imagen :String?) : Int{

        var copias = 0

        for (carta in mainDeck)
        {
            if( imagen == carta.imagen){
                copias++
            }
        }
        return copias
    }


    fun probabilidadCartaEnMano(mainDeck: Int, copias: Int, mano: Int): Double {
        val coef1 = coeficienteBinomial(copias, 1)
        val coef2 = coeficienteBinomial(mainDeck - copias, mano - 1)
        val coef3 = coeficienteBinomial(mainDeck, mano)
        return coef1 * coef2 / coef3
    }

    fun coeficienteBinomial(mano: Int, numcopias: Int): Double {
        var result = 1.0
        for (i in 1..numcopias) {
            result *= (mano - numcopias + i) / i.toDouble()
        }
        return result
    }


}