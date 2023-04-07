package com.example.mybinder

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mybinder.controllers.DatabaseHelper

class DetallesCartaMon: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalles_mon_layout)

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

        if(categoria2Rec == "Xyz"){
            textoNivel.setText("Rango:")
        }
        else if(categoria2Rec == "Link"){
            textoNivel.setText("Link:")
            campoDefensa.visibility = View.INVISIBLE
        }
        else{
            textoNivel.setText("Nivel:")
        }

        textoEscala.visibility = View.INVISIBLE
        campoEscala.visibility = View.INVISIBLE

        if(categoria2Rec == "Pendulo"){
            textoEscala.visibility = View.VISIBLE
            campoEscala.visibility = View.VISIBLE
        }

        botonEdit.setOnClickListener{
            val intent = Intent(applicationContext, EditMonCard::class.java)

            intent.putExtra("id", idRec)
            intent.putExtra("categoria", categoriaRec)
            intent.putExtra("categoria2", categoria2Rec)
            intent.putExtra("nombre", nombreRec)
            intent.putExtra("atributo",atributoRec)
            intent.putExtra("nivel",nivelRec)
            intent.putExtra("tipo", tipoRec)
            intent.putExtra("ataque",ataqueRec)
            intent.putExtra("defensa",defensaRec)
            intent.putExtra("codigo", codigoRec)
            intent.putExtra("escala", escalaRec)
            intent.putExtra("cantidad", cantidadRec)
            intent.putExtra("imagen", imagenRec)

            startActivity(intent)
        }

        botonDel.setOnClickListener{

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

    }
}