package com.example.mybinder.Detalles

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Funciones.EditSTCard
import com.example.mybinder.Listas.Filtrado_list
import com.example.mybinder.Listas.Lista_cambio
import com.example.mybinder.Listas.MainActivity
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper

class DetallesCartaST: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalles_st_layout)

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

        val idRecibido = intent.getIntExtra("id",0)
        val tipoRecibido = intent.getStringExtra("tipo")
        val codigoRecibido = intent.getStringExtra("codigo")
        val categoriaRecibido = intent.getStringExtra("categoria")
        val cantidadRecibido = intent.getIntExtra("cantidad",0)
        val nombreRecibido = intent.getStringExtra("nombre")
        val imagenRecibido = intent.getStringExtra("imagen")
        var cambioRecibido = intent.getIntExtra("cambio", 0)

        val databaseHelper = DatabaseHelper(this@DetallesCartaST)
        val lista = databaseHelper.getAllSpellTraps()

        val campoNombre = findViewById<TextView>(R.id.card_nameDST)
        val campoImagen = findViewById<ImageView>(R.id.imageViewDST)
        val campoCategoria = findViewById<TextView>(R.id.categoriaDST)
        val campoTipo = findViewById<TextView>(R.id.tipoDST)
        val campoCantidad = findViewById<TextView>(R.id.cantidadDST)
        val campoCodigo = findViewById<TextView>(R.id.codigoDST)

        val botonEdit = findViewById<Button>(R.id.edit_btnST)
        val botonDel = findViewById<Button>(R.id.delete_btnST)
        val colecion_btn = findViewById<Button>(R.id.coleccion_btn)


        for(spell in lista){
            if(spell.id == idRecibido){
                cambioRecibido = if (spell.cambio) 1 else 0
            }
        }

        if( cambioRecibido == 0){
            colecion_btn.setText("Mandar a cambio")
        }else{
            colecion_btn.setText("Quitar de Cambio")
        }

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
        campoCategoria.setOnClickListener{

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre","")
            intent.putExtra("categoria", categoriaRecibido)
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

        campoTipo.setOnClickListener {

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre", "")
            intent.putExtra("categoria", categoriaRecibido)
            intent.putExtra("tipo", tipoRecibido)
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

        campoCodigo.setOnClickListener{

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre","")
            intent.putExtra("categoria", "")
            intent.putExtra("tipo", "")
            intent.putExtra("codigo", codigoRecibido)
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

        campoNombre.setOnClickListener{

            val intent = Intent(applicationContext, Filtrado_list::class.java)

            intent.putExtra("nombre",nombreRecibido)
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

            for(spell in lista){
                if(spell.id == idRecibido){
                    cambio = spell.cambio
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

            val spell_trap = Spells_Traps(idRecibido,nombreRecibido ,categoriaRecibido,tipoRecibido,codigoRecibido,cantidadRecibido,imagenRecibido, cambio)

            databaseHelper.updateSpellTrap(spell_trap)
            val intent = Intent(this, Lista_cambio::class.java)
            startActivity(intent)
        }
    }
}