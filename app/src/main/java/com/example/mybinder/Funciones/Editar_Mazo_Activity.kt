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

class Editar_Mazo_Activity: AppCompatActivity(){

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

        val idRec = intent.getIntExtra("id", 0)
        val nombreRec = intent.getStringExtra("nombre")

        val campoNombre = findViewById<EditText>(R.id.name_newDeck)
        val save_btn = findViewById<Button>(R.id.guardarDeck_btn)
        val limpiar = findViewById<Button>(R.id.limpiarDeck)

        campoNombre.setText(nombreRec)

        val databaseHelper = DatabaseHelper(this)
        mainDeck = databaseHelper.obtenerImagenesIntermedia(databaseHelper.readableDatabase, idRec)

        var cantidad = findViewById<TextView>(R.id.cantidadDeck)
        cantidad.text = mainDeck.size.toString()

        val recyclerView = findViewById<RecyclerView>(R.id.mainDeckSPN)
        val layoutM = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager = layoutM

        mazoAdapter = CardMazoAdapter(mainDeck, object  : OnItemClickListener{
            override fun onItemClick(position: Int) {

                mainDeck.removeAt(position)
                mazoAdapter.notifyItemRemoved(position)
                cantidad.text = mainDeck.size.toString()
            }
        })
        recyclerView.adapter = mazoAdapter


        val monstruosRecyclerView = RecyclerView(this)
        val monstruosLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        monstruosRecyclerView.layoutManager = monstruosLayoutManager
        var monstruosList = databaseHelper.buscarMonstruosNombre(camponombre)
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
                                    Toast.makeText(this@Editar_Mazo_Activity, "Maximas copias alcanzadas", Toast.LENGTH_SHORT).show()
                                    break
                                }
                            }
                        }

                        if (count < 3) {

                            Toast.makeText(this@Editar_Mazo_Activity, "Carta Añadida al main", Toast.LENGTH_SHORT).show()
                            mainDeck.add(Carta(0,monstruosList[position].imagen, idRec))
                            mazoAdapter.notifyDataSetChanged()
                            mazoAdapter.notifyItemChanged(position)


                            cantidad.text = mainDeck.size.toString()
                        }
                    } else {
                        Toast.makeText(this@Editar_Mazo_Activity, "Mazo completo", Toast.LENGTH_SHORT).show()

                    }
                }


            })
        monstruosRecyclerView.adapter = monstruosAdapter
        mazoAdapter.notifyDataSetChanged()

        // Crear un RecyclerView para la lista de spells y traps

        val spellsTrapsRecyclerView = RecyclerView(this)
        val spellsTrapsLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        spellsTrapsRecyclerView.layoutManager = spellsTrapsLayoutManager

        var spellsTrapsList = databaseHelper.buscarSpellTrapsNombre(camponombre)
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
                                Toast.makeText(this@Editar_Mazo_Activity, "Maximas copias alcanzadas", Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                    }

                    if (count < 3) {
                        Toast.makeText(this@Editar_Mazo_Activity, "Carta Añadida al main", Toast.LENGTH_SHORT).show()
                        mainDeck.add(Carta(0,spellsTrapsList[position].imagen,idRec))
                        mazoAdapter.notifyDataSetChanged()
                        mazoAdapter.notifyItemChanged(position)

                        cantidad.text = mainDeck.size.toString()
                    }
                } else {
                    Toast.makeText(
                        this@Editar_Mazo_Activity, "Mazo completo", Toast.LENGTH_SHORT).show()

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
            databaseHelper.deleteAllCardDeck(idRec)

            nombre = campoNombre.text.toString()
            mazo.nombre = nombre
            mazo.id = idRec

            println(mainDeck)
            databaseHelper.updateMazo(mazo)

            for(carta in mainDeck) {
                databaseHelper.insertCard_Mazo(idRec, carta)

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