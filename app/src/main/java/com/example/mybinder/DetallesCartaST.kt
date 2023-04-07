package com.example.mybinder

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mybinder.controllers.DatabaseHelper

class DetallesCartaST: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalles_st_layout)

        val idRecibido = intent.getIntExtra("id",0)
        val tipoRecibido = intent.getStringExtra("tipo")
        val codigoRecibido = intent.getStringExtra("codigo")
        val categoriaRecibido = intent.getStringExtra("categoria")
        val cantidadRecibido = intent.getIntExtra("cantidad",0)
        val nombreRecibido = intent.getStringExtra("nombre")
        val imagenRecibido = intent.getStringExtra("imagen")


        val campoNombre = findViewById<TextView>(R.id.card_nameDST)
        val campoImagen = findViewById<ImageView>(R.id.imageViewDST)
        val campoCategoria = findViewById<TextView>(R.id.categoriaDST)
        val campoTipo = findViewById<TextView>(R.id.tipoDST)
        val campoCantidad = findViewById<TextView>(R.id.cantidadDST)
        val campoCodigo = findViewById<TextView>(R.id.codigoDST)

        val botonEdit = findViewById<Button>(R.id.edit_btnST)
        val botonDel = findViewById<Button>(R.id.delete_btnST)

        campoNombre.setText(nombreRecibido)
        campoTipo.setText(tipoRecibido)
        campoCategoria.setText(categoriaRecibido)
        campoCodigo.setText(codigoRecibido)
        campoCantidad.setText(cantidadRecibido.toString())

        Glide.with(this)
            .load(imagenRecibido)
            .into(campoImagen)



        botonEdit.setOnClickListener{
            val intent = Intent(applicationContext, EditSTCard::class.java)

            intent.putExtra( "id",idRecibido)
            intent.putExtra( "imagen",imagenRecibido)
            intent.putExtra( "tipo",tipoRecibido)
            intent.putExtra( "codigo", codigoRecibido)
            intent.putExtra( "categoria", categoriaRecibido)
            intent.putExtra( "cantidad", cantidadRecibido)
            intent.putExtra( "nombre",nombreRecibido)

            startActivity(intent)
        }
        botonDel.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Estás seguro de realizar esta acción?")
            builder.setPositiveButton("Sí") { dialog, which ->
                val databaseHelper = DatabaseHelper(this@DetallesCartaST)
                databaseHelper.deleteSpellTrap(idRecibido)
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("No", null)
            builder.show()

        }


    }
}