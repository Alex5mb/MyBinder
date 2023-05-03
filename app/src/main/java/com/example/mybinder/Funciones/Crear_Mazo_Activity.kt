package com.example.mybinder.Funciones

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Adapters.CardMazoAdapter
import com.example.mybinder.Adapters.MonstruoAdapter
import com.example.mybinder.Adapters.SpellTrapAdapter
import com.example.mybinder.Listas.Mazos_Activity
import com.example.mybinder.Model.Carta
import com.example.mybinder.Model.Mazo
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper
import com.example.mybinder.controllers.OnItemClickListener



class Crear_Mazo_Activity : AppCompatActivity() {

    lateinit var mazoAdapter: CardMazoAdapter
    var mainDeck = mutableListOf<Carta>()
    var nombre: String = ""
    val mazo = Mazo(0, nombre)
    var camponombre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_deck_layout)

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


        val databaseHelper = DatabaseHelper(this@Crear_Mazo_Activity)
        val ultimo = databaseHelper.obtenerUltimoIdDeTablaMazo(this)
        println(ultimo)
        val campoNombre = findViewById<EditText>(R.id.name_newDeck)

        val save_btn = findViewById<Button>(R.id.guardarDeck_btn)
        val limpiar = findViewById<Button>(R.id.limpiarDeck)
        var cantidad = findViewById<TextView>(R.id.cantidadDeck)

        cantidad.text = mainDeck.size.toString()

        val recyclerView = findViewById<RecyclerView>(R.id.mainDeckSPN)
        val layoutM = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager = layoutM


            mazoAdapter = CardMazoAdapter(mainDeck, object  : OnItemClickListener{
            override fun onItemClick(position: Int) {

                databaseHelper.deleteTablaIntermedia(mainDeck[position].id)
                println(mainDeck)
                mazoAdapter.notifyDataSetChanged()

                mainDeck.clear()
                mainDeck = databaseHelper.obtenerImagenesIntermedia(databaseHelper.readableDatabase, ultimo + 1)
                cantidad.setText(mainDeck.size.toString())

            }
        })
        recyclerView.adapter = mazoAdapter
        mazoAdapter.notifyDataSetChanged()


        val monstruosRecyclerView = RecyclerView(this)
        val monstruosLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        monstruosRecyclerView.layoutManager = monstruosLayoutManager
        val monstruosList = databaseHelper.buscarMonstruosNombre(camponombre)
        val monstruosAdapter =
            MonstruoAdapter(monstruosList, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    if (mainDeck.size < 60) {
                        val temp = mutableListOf<Carta>()
                        temp.addAll(mainDeck)

                        var count = 0
                        for (monstruo in temp) {
                            if (monstruo.imagen == monstruosList[position].imagen) {
                                count++
                                if (count >= 3) {
                                    Toast.makeText(this@Crear_Mazo_Activity, "Maximas copias alcanzadas", Toast.LENGTH_SHORT).show()
                                    break
                                }
                            }
                        }

                        if (count < 3) {
                            mainDeck.add(Carta(0,monstruosList[position].imagen, ultimo + 1))
                            Toast.makeText(this@Crear_Mazo_Activity, "Carta Añadida al main", Toast.LENGTH_SHORT).show()

                            mazoAdapter.notifyItemChanged(position)
                            mazoAdapter.notifyDataSetChanged()
                            cantidad.setText(mainDeck.size.toString())

                            println( mainDeck)
                        }
                    } else {
                        Toast.makeText(this@Crear_Mazo_Activity, "Mazo completo", Toast.LENGTH_SHORT).show()

                    }
                }


            })
        monstruosRecyclerView.adapter = monstruosAdapter

        // Crear un RecyclerView para la lista de spells y traps

        val spellsTrapsRecyclerView = RecyclerView(this)
        val spellsTrapsLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        spellsTrapsRecyclerView.layoutManager = spellsTrapsLayoutManager

        val spellsTrapsList = databaseHelper.buscarSpellTrapsNombre(camponombre)
        val spellsTrapsAdapter = SpellTrapAdapter(spellsTrapsList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {


                if (mainDeck.size < 60) {
                    val temp = mutableListOf<Carta>()
                    temp.addAll(mainDeck)

                    var count = 0
                    for (spelltrap in temp) {
                        if (spelltrap.imagen == spellsTrapsList[position].imagen) {
                            count++
                            if (count >= 3) {
                                Toast.makeText(this@Crear_Mazo_Activity, "Maximas copias alcanzadas", Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                    }

                    if (count < 3) {
                       mainDeck.add(Carta(0,spellsTrapsList[position].imagen,ultimo + 1))
                        Toast.makeText(this@Crear_Mazo_Activity, "Carta Añadida al main", Toast.LENGTH_SHORT).show()
                        mazoAdapter.notifyItemChanged(position)
                        mazoAdapter.notifyDataSetChanged()
                        cantidad.setText(mainDeck.size.toString())
                    }
                } else {
                    Toast.makeText(
                        this@Crear_Mazo_Activity, "Mazo completo", Toast.LENGTH_SHORT).show()

                }

            }

        })
        spellsTrapsRecyclerView.adapter = spellsTrapsAdapter
        spellsTrapsAdapter.notifyDataSetChanged()

        val Rmonstruo = findViewById<RecyclerView>(R.id.recyclerMCard)

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Rmonstruo.layoutManager = layoutManager2
        Rmonstruo.adapter = monstruosAdapter

        val RspellTrap = findViewById<RecyclerView>(R.id.recyclerSPCard)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RspellTrap.layoutManager = layoutManager
        RspellTrap.adapter = spellsTrapsAdapter

        save_btn.setOnClickListener{

            nombre = campoNombre.text.toString()
            mazo.nombre = nombre
            var databaseHelper = DatabaseHelper(this@Crear_Mazo_Activity)

            databaseHelper.insertMazo(mazo)

            for(carta in mainDeck) {
                databaseHelper.insertCard_Mazo(ultimo +1 , carta)
                println(carta)

            }
                val intent = Intent(this, Mazos_Activity::class.java)
                startActivity(intent)

        }

        limpiar.setOnClickListener{

            mainDeck.clear()
            mazoAdapter.notifyDataSetChanged()
            cantidad.text = mainDeck.size.toString()

        }

    }

}