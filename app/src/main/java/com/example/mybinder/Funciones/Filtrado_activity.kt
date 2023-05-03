package com.example.mybinder.Funciones

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Listas.Filtrado_list
import com.example.mybinder.R

var nombre: String = ""
var categoria: String = ""
var tipo: String = ""
var codigo: String = ""
var categoria2: String = ""
var nivel: Int = 0
var nivel2: Int = 12
var ataque: Int = 0
var ataque2: Int = 9000
var defensa: Int = 0
var defensa2: Int = 9000
var atributo: String = ""
var escala: Int = 0
var escala2: Int = 13


class Filtrado_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filtrado_activity)

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


        val filtrado_btn = findViewById<Button>(R.id.filtrado_btn)

        val campoNombre = findViewById<EditText>(R.id.nombreFilCard)
        val spinnerCategorias = findViewById<Spinner>(R.id.categoriaFilCard)
        val spinnerTipos = findViewById<Spinner>(R.id.tipoFilCard)
        val campoCodigo = findViewById<EditText>(R.id.codigoFilCard)
        val categorias2Text = findViewById<TextView>(R.id.categoria2FilText)
        val spinnerCategorias2 = findViewById<Spinner>(R.id.categoria2FilCard)
        val nivelText = findViewById<TextView>(R.id.nivelFilText)
        val campoNivel = findViewById<EditText>(R.id.nivelFilCard)
        val separadorNivel = findViewById<TextView>(R.id.middlelvlFil)
        val campoNivel2 = findViewById<EditText>(R.id.nivel2FilCard)
        val ataqueText = findViewById<TextView>(R.id.atkFilText)
        val campoAtaque = findViewById<EditText>(R.id.atackFilCard)
        val separadorAtack = findViewById<TextView>(R.id.atackFilmiddle)
        val campoAtaque2 = findViewById<EditText>(R.id.atack2FilCard)
        val defensaText = findViewById<TextView>(R.id.deffFilText)
        val campoDefensa = findViewById<EditText>(R.id.deffFilCard)
        val separadorDef = findViewById<TextView>(R.id.deffFilmiddle)
        val campoDefensa2 = findViewById<EditText>(R.id.deff2FilCard)
        val atributoText = findViewById<TextView>(R.id.atributoFilTxt)
        val spinnerAtributo = findViewById<Spinner>(R.id.atributoFilCard)
        val escalaText = findViewById<TextView>(R.id.escalaFilTxt)
        val campoEscala = findViewById<EditText>(R.id.escalaFilCard)
        val separadorEscala = findViewById<TextView>(R.id.escalaFilmiddle)
        val campoEscala2 = findViewById<EditText>(R.id.escala2FilCard)


        val listaCategorias = resources.getStringArray(R.array.categorias)
        val categoriaAdapter = ArrayAdapter(this, R.layout.spinner_layout, listaCategorias)
        spinnerCategorias.adapter = categoriaAdapter

        val listaCategoria2 = resources.getStringArray(R.array.categoria2M)
        val categorias2Adapter = ArrayAdapter(this, R.layout.spinner_layout, listaCategoria2)
        spinnerCategorias2.adapter = categorias2Adapter

        spinnerCategorias2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    categoria2 = parent?.getItemAtPosition(position) as String

                    campoEscala.visibility =
                        if (categoria2 == "Pendulo") View.VISIBLE else View.GONE
                    escalaText.visibility = if (categoria2 == "Pendulo") View.VISIBLE else View.GONE
                    campoEscala.visibility = if (categoria2 == "Pendulo") View.VISIBLE else View.GONE
                    campoEscala2.visibility = if (categoria2 == "Pendulo") View.VISIBLE else View.GONE
                    separadorEscala.visibility = if (categoria2 == "Pendulo") View.VISIBLE else View.GONE

                    if(categoria2 =="Xyz"){

                        nivelText.text = "Rango:"
                    }
                    else if(categoria2 == "Link"){
                        nivelText.text = "Link:"
                    }
                    else{
                        nivelText.text = "Nivel:"
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        val listaAtributos = resources.getStringArray(R.array.atributo)
        val atributosAdapter =
            ArrayAdapter(this, R.layout.spinner_layout, listaAtributos)
        spinnerAtributo.adapter = atributosAdapter

        spinnerAtributo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedAtributo = parent?.getItemAtPosition(position) as String
                    atributo = selectedAtributo
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //Hacemos que el spinner de categoria optenga un valor cuando se selecciona uno
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        spinnerCategorias2.visibility = View.INVISIBLE
                        spinnerAtributo.visibility = View.INVISIBLE
                        campoNivel.visibility = View.INVISIBLE
                        campoNivel2.visibility = View.INVISIBLE
                        campoAtaque.visibility = View.INVISIBLE
                        campoAtaque2.visibility = View.INVISIBLE
                        campoDefensa.visibility = View.INVISIBLE
                        campoDefensa2.visibility = View.INVISIBLE
                        campoEscala.visibility = View.INVISIBLE
                        campoEscala2.visibility = View.INVISIBLE
                        categorias2Text.visibility = View.INVISIBLE
                        separadorNivel.visibility = View.INVISIBLE
                        separadorAtack.visibility = View.INVISIBLE
                        separadorDef.visibility = View.INVISIBLE
                        separadorEscala.visibility = View.INVISIBLE
                        nivelText.visibility = View.INVISIBLE
                        ataqueText.visibility = View.INVISIBLE
                        defensaText.visibility = View.INVISIBLE
                        escalaText.visibility = View.INVISIBLE
                        atributoText.visibility = View.INVISIBLE


                    }
                    1 -> {
                        categoria = "Monstruo"

                        spinnerCategorias2.visibility = View.VISIBLE
                        spinnerAtributo.visibility = View.VISIBLE
                        campoNivel.visibility = View.VISIBLE
                        campoNivel2.visibility = View.VISIBLE
                        campoAtaque.visibility = View.VISIBLE
                        campoAtaque2.visibility = View.VISIBLE
                        campoDefensa.visibility = View.VISIBLE
                        campoDefensa2.visibility = View.VISIBLE
                        campoEscala.visibility = View.VISIBLE
                        campoEscala2.visibility = View.VISIBLE
                        categorias2Text.visibility = View.VISIBLE
                        separadorNivel.visibility = View.VISIBLE
                        separadorAtack.visibility = View.VISIBLE
                        separadorDef.visibility = View.VISIBLE
                        separadorEscala.visibility = View.VISIBLE
                        nivelText.visibility = View.VISIBLE
                        ataqueText.visibility = View.VISIBLE
                        defensaText.visibility = View.VISIBLE
                        escalaText.visibility = View.VISIBLE
                        atributoText.visibility = View.VISIBLE


                        val listaTipo = resources.getStringArray(R.array.tipoM)
                        val tipoAdapter =
                            ArrayAdapter(this@Filtrado_activity, R.layout.spinner_layout, listaTipo)
                        spinnerTipos.adapter = tipoAdapter

                        spinnerTipos.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val selectedTipo = parent?.getItemAtPosition(position) as String
                                    tipo = selectedTipo
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }

                            }

                    }
                    2 -> {
                        categoria = "Magica"

                        spinnerCategorias2.visibility = View.INVISIBLE
                        spinnerAtributo.visibility = View.INVISIBLE
                        campoNivel.visibility = View.INVISIBLE
                        campoNivel2.visibility = View.INVISIBLE
                        campoAtaque.visibility = View.INVISIBLE
                        campoAtaque2.visibility = View.INVISIBLE
                        campoDefensa.visibility = View.INVISIBLE
                        campoDefensa2.visibility = View.INVISIBLE
                        campoEscala.visibility = View.INVISIBLE
                        campoEscala2.visibility = View.INVISIBLE
                        categorias2Text.visibility = View.INVISIBLE
                        separadorNivel.visibility = View.INVISIBLE
                        separadorAtack.visibility = View.INVISIBLE
                        separadorDef.visibility = View.INVISIBLE
                        separadorEscala.visibility = View.INVISIBLE
                        nivelText.visibility = View.INVISIBLE
                        ataqueText.visibility = View.INVISIBLE
                        defensaText.visibility = View.INVISIBLE
                        escalaText.visibility = View.INVISIBLE
                        atributoText.visibility = View.INVISIBLE

                        val listaTipo = resources.getStringArray(R.array.tipoS)
                        val tipoAdapter =
                            ArrayAdapter(this@Filtrado_activity, R.layout.spinner_layout, listaTipo)
                        spinnerTipos.adapter = tipoAdapter

                        spinnerTipos.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val selectedTipo = parent?.getItemAtPosition(position) as String
                                    tipo = selectedTipo
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }
                            }


                    }
                    3 -> {
                        categoria = "Trampa"

                        spinnerCategorias2.visibility = View.INVISIBLE
                        spinnerAtributo.visibility = View.INVISIBLE
                        campoNivel.visibility = View.INVISIBLE
                        campoNivel2.visibility = View.INVISIBLE
                        campoAtaque.visibility = View.INVISIBLE
                        campoAtaque2.visibility = View.INVISIBLE
                        campoDefensa.visibility = View.INVISIBLE
                        campoDefensa2.visibility = View.INVISIBLE
                        campoEscala.visibility = View.INVISIBLE
                        campoEscala2.visibility = View.INVISIBLE
                        categorias2Text.visibility = View.INVISIBLE
                        separadorNivel.visibility = View.INVISIBLE
                        separadorAtack.visibility = View.INVISIBLE
                        separadorDef.visibility = View.INVISIBLE
                        separadorEscala.visibility = View.INVISIBLE
                        nivelText.visibility = View.INVISIBLE
                        ataqueText.visibility = View.INVISIBLE
                        defensaText.visibility = View.INVISIBLE
                        escalaText.visibility = View.INVISIBLE
                        atributoText.visibility = View.INVISIBLE

                        val listaTipo = resources.getStringArray(R.array.tipoT)
                        val tipoAdapter =
                            ArrayAdapter(this@Filtrado_activity, R.layout.spinner_layout, listaTipo)
                        spinnerTipos.adapter = tipoAdapter

                        spinnerTipos.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val selectedTipo = parent?.getItemAtPosition(position) as String
                                    tipo = selectedTipo
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }
                            }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        filtrado_btn.setOnClickListener {

            nombre = campoNombre.text.toString()
            codigo = campoCodigo.text.toString()

            if(campoNivel.text.toString() == ""){
                nivel
            }
            else{
                nivel = campoNivel.text.toString().toInt()
            }

            if(campoNivel2.text.toString() == ""){
                nivel2
            }
            else{
                nivel2 = campoNivel2.text.toString().toInt()
            }

            if(campoAtaque.text.toString() == ""){
                ataque
            }
            else{
                ataque = campoAtaque.text.toString().toInt()
            }

            if(campoAtaque2.text.toString() == ""){
                ataque2
            }
            else{
                ataque2 = campoAtaque2.text.toString().toInt()
            }

            if(campoDefensa.text.toString() == ""){
                defensa
            }
            else{
                defensa = campoDefensa.text.toString().toInt()
            }

            if(campoDefensa2.text.toString() == ""){
                defensa
            }
            else{
                defensa2 = campoDefensa2.text.toString().toInt()
            }

            if(campoEscala.text.toString() == ""){
                escala
            }
            else{
                escala = campoEscala.text.toString().toInt()
            }

            if(campoEscala2.text.toString() == ""){
                escala2
            }
            else{
                escala2 = campoEscala2.text.toString().toInt()
            }
            val intent = Intent(this, Filtrado_list::class.java)

            intent.putExtra("nombre", nombre)
            intent.putExtra("categoria", categoria)
            intent.putExtra("tipo", tipo)
            intent.putExtra("codigo", codigo)
            intent.putExtra("categoria2", categoria2)
            intent.putExtra("nivel", nivel)
            intent.putExtra("nivel2", nivel2)
            intent.putExtra("ataque", ataque)
            intent.putExtra("ataque2", ataque2)
            intent.putExtra("defensa", defensa)
            intent.putExtra("defensa2", defensa2)
            intent.putExtra("atributo", atributo)
            intent.putExtra("escala", escala)
            intent.putExtra("escala2", escala2)

            startActivity(intent)
        }
    }
}