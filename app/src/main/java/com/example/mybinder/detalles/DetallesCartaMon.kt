package com.example.mybinder.Detalles

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Funciones.EditMonCard
import com.example.mybinder.Listas.Filtrado_list
import com.example.mybinder.Listas.Lista_cambio
import com.example.mybinder.Listas.MainActivity
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper

class DetallesCartaMon: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalles_mon_layout)

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
        val categoriaRec = intent.getStringExtra("categoria") ?: "Monstruo"
        val categoria2Rec = intent.getStringExtra("categoria2") ?: ""
        val nombreRec = intent.getStringExtra("nombre") ?: ""
        val atributoRec = intent.getStringExtra("atributo") ?: ""
        val nivelRec = intent.getIntExtra("nivel", 0)
        val tipoRec = intent.getStringExtra("tipo") ?: ""
        val ataqueRec = intent.getIntExtra("ataque", 0)
        val defensaRec = intent.getIntExtra("defensa", 0)
        val codigoRec = intent.getStringExtra("codigo") ?: ""
        val escalaRec = intent.getIntExtra("escala", 0)
        val cantidadRec = intent.getIntExtra("cantidad", 0)
        val imagenRec = intent.getStringExtra("imagen") ?: ""
        var cambioRec = intent.getIntExtra("cambio", 0)

        val campoNombre = findViewById<TextView>(R.id.card_nameDMon)
        val campoCategoria = findViewById<TextView>(R.id.categoriaDMon)
        val campoCategoria2 = findViewById<TextView>(R.id.categoria2DMon)
        val campoAtributo = findViewById<TextView>(R.id.atributoDMon)
        val textoNivel = findViewById<TextView>(R.id.nivelTextDMon)
        val campoNivel = findViewById<TextView>(R.id.nivelDMon)
        val campoTipo = findViewById<TextView>(R.id.tipoDMon)
        val campoAtaque = findViewById<TextView>(R.id.ataqueDMon)
        val campoDefensa = findViewById<TextView>(R.id.defensaDMon)
        val campoCodigo = findViewById<TextView>(R.id.codigoDMon)
        val textoEscala = findViewById<TextView>(R.id.escalaTextD)
        val campoEscala = findViewById<TextView>(R.id.escalaDMon)
        val campoCantidad = findViewById<TextView>(R.id.cantidadDMon)
        val campoImagen = findViewById<ImageView>(R.id.imageViewDMon)

        val botonEdit = findViewById<Button>(R.id.edit_btnMon)
        val botonDel = findViewById<Button>(R.id.delete_btnMon)
        val colecion_btn = findViewById<Button>(R.id.coleccion_btn)

        val databaseHelper = DatabaseHelper(this@DetallesCartaMon)
        val lista = databaseHelper.getAllMonstruos()

        for(monstruo in lista){
            if(monstruo.id == idRec){
                cambioRec = if (monstruo.cambio) 1 else 0
            }
        }

        if( cambioRec == 0){
            colecion_btn.setText("Mandar a cambio")
        }else{
            colecion_btn.setText("Quitar de Cambio")
        }

        Glide.with(this)
            .load(imagenRec)
            .into(campoImagen)

        campoNombre.setText(nombreRec)
        campoTipo.setText(tipoRec)
        campoCategoria.setText(categoriaRec)
        campoCodigo.setText(codigoRec)
        campoCantidad.setText(cantidadRec.toString())
        campoCategoria2.setText(categoria2Rec)
        campoAtributo.setText(atributoRec)
        campoNivel.setText(nivelRec.toString())
        campoAtaque.setText(ataqueRec.toString())
        campoDefensa.setText(defensaRec.toString())
        campoEscala.setText(escalaRec.toString())

        if (categoria2Rec == "Xyz") {
            textoNivel.setText("Rango:")
        } else if (categoria2Rec == "Link") {
            textoNivel.setText("Link:")
            campoDefensa.visibility = View.INVISIBLE
        } else {
            textoNivel.setText("Nivel:")
        }

        textoEscala.visibility = View.INVISIBLE
        campoEscala.visibility = View.INVISIBLE

        if (categoria2Rec == "Pendulo") {
            textoEscala.visibility = View.VISIBLE
            campoEscala.visibility = View.VISIBLE
        }

        botonEdit.setOnClickListener {
            val intent = Intent(applicationContext, EditMonCard::class.java)

            intent.putExtra("id", idRec)
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("categoria2", categoria2Rec)
            intent.putExtra("nombre", nombreRec)
            intent.putExtra("atributo", atributoRec)
            intent.putExtra("nivel", nivelRec)
            intent.putExtra("tipo", tipoRec)
            intent.putExtra("ataque", ataqueRec)
            intent.putExtra("defensa", defensaRec)
            intent.putExtra("codigo", codigoRec)
            intent.putExtra("escala", escalaRec)
            intent.putExtra("cantidad", cantidadRec)
            intent.putExtra("imagen", imagenRec)

            startActivity(intent)
        }

        botonDel.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Estás seguro de realizar esta acción?")
            builder.setPositiveButton("Sí") { dialog, which ->
                val databaseHelper = DatabaseHelper(this@DetallesCartaMon)
                databaseHelper.deleteMonstruo(idRec)
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("No", null)
            builder.show()

        }

        campoCategoria.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)

        }

        campoAtributo.setOnClickListener {
            val intent = Intent(applicationContext, Filtrado_list::class.java)


            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", atributoRec)
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)
        }

        campoTipo.setOnClickListener{
            val intent = Intent(applicationContext, Filtrado_list::class.java)


            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", tipoRec)
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)
        }

        campoCodigo.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", codigoRec)
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)

        }

        campoCategoria2.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", categoria2Rec)
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)

        }

        campoNivel.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", nivelRec)
            intent.putExtra("nivel2", nivelRec)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)

        }

        campoAtaque.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", ataqueRec)
            intent.putExtra("ataque2", ataqueRec)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)

        }

        campoDefensa.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", defensaRec)
            intent.putExtra("defensa2", defensaRec)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", 0)
            intent.putExtra("escala2", 13)

            startActivity(intent)

        }

        campoEscala.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", "")
            intent.putExtra("categoria2", "")
            intent.putExtra("nivel", 0)
            intent.putExtra("nivel2", 12)
            intent.putExtra("ataque", 0)
            intent.putExtra("ataque2", 9000)
            intent.putExtra("defensa", 0)
            intent.putExtra("defensa2", 9000)
            intent.putExtra("atributo", "")
            intent.putExtra("escala", escalaRec)
            intent.putExtra("escala2", escalaRec)

            startActivity(intent)

        }

            campoNombre.setOnClickListener{

                val intent = Intent(applicationContext, Filtrado_list::class.java)

                intent.putExtra("nombre",nombreRec)
                intent.putExtra("categoria","")
                intent.putExtra("tipo", "")
                intent.putExtra("codigo", "")
                intent.putExtra("categoria2", "")
                intent.putExtra("nivel", 0)
                intent.putExtra("nivel2",12)
                intent.putExtra("ataque", 0)
                intent.putExtra("ataque2", 9000)
                intent.putExtra("defensa", 0)
                intent.putExtra("defensa2", 9000)
                intent.putExtra("atributo", "")
                intent.putExtra("escala", 0)
                intent.putExtra("escala2", 13)

                startActivity(intent)
            }


        colecion_btn.setOnClickListener{

            var cambio: Boolean = false

            for(monstruo in lista){
                if(monstruo.id == idRec){
                    cambio = monstruo.cambio
                }
            }

            if(cambio == false){
                colecion_btn.setText("Quitar de Cambio")
                cambio = true
            }
            else if(cambio == true){
                colecion_btn.setText("Mandar a cambio")
                cambio = false
            }

            val monstruo = Monstruo(idRec, "Monstruo", categoria2Rec, nombreRec, atributoRec, nivelRec, tipoRec,
                ataqueRec, defensaRec, codigoRec, escalaRec, cantidadRec, imagenRec, cambio)

            databaseHelper.updateMonstruo(monstruo)

            val intent = Intent(this, Lista_cambio::class.java)
            startActivity(intent)
        }
    }
}