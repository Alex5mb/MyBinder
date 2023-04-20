package com.example.mybinder.Funciones

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Listas.MainActivity
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper
import com.example.mybinder.controllers.MyApplication.Companion.dbHelper
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditSTCard: AppCompatActivity() {



    private var categoria: String = ""
    private var categoria2: String = ""
    private var nombre: String = ""
    private var atributo: String = ""
    private var nivel: Int? = null
    private var tipo: String = ""
    private var ataque: Int? = null
    private var defensa: Int? = null
    private var codigo: String = ""
    private var escala: Int? = null
    private var cantidad: Int = 1
    private var imagen: ImageView? = null
    private val REQUEST_CAMERA_PERMISSION = 1
    private var currentPhotoPath: String = ""
    private var cambio: Boolean = false


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_st_card)

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
        val imagenRecibido = intent.getStringExtra("imagen")
        val tipoRecibido = intent.getStringExtra("tipo")
        val codigoRecibido = intent.getStringExtra("codigo")
        val categoriaRecibido = intent.getStringExtra("categoria")
        val cantidadRecibido = intent.getIntExtra("cantidad",0)
        val nombreRecibido = intent.getStringExtra("nombre")

        AñadirCarta.dbmonster = DatabaseHelper(this@EditSTCard)


        val categoria2Txt = findViewById<TextView>(R.id.categoria2EditText)
        val nivelTxt = findViewById<TextView>(R.id.nivelEditText)
        val ataqueTxt = findViewById<TextView>(R.id.atkEditText)
        val defensaTxt = findViewById<TextView>(R.id.deffEditText)
        val atributoTxt = findViewById<TextView>(R.id.atributoEditTxt)
        val escalaTxt = findViewById<TextView>(R.id.escalaEditTxt)
        val crear_btn = findViewById<Button>(R.id.crearEdit)
        val foto_btn = findViewById<Button>(R.id.imagen_btnEdit)
        val imagenV = findViewById<ImageView>(R.id.imageViewEditCard)

        if (imagenRecibido != null) {
            currentPhotoPath = imagenRecibido
            Glide.with(this)
                .load(imagenRecibido)
                .into(imagenV)
        }



        //Campos TextView
        val campoNombre = findViewById<EditText>(R.id.nombreEditCard)
        val campoNivel = findViewById<EditText>(R.id.nivelEditCard)
        val campoATK = findViewById<EditText>(R.id.atackEditCard)
        val campoDEF = findViewById<EditText>(R.id.deffEditCard)
        val campoEscala = findViewById<EditText>(R.id.escalaEditCard)
        val campoCantidad = findViewById<EditText>(R.id.cantidadEditCard)
        val campoCodigo = findViewById<EditText>(R.id.codigoEditCard)
        imagen = findViewById(R.id.imageViewEditCard)


        campoNombre.setText(nombreRecibido)
        campoCantidad.setText(cantidadRecibido.toString())
        campoCodigo.setText(codigoRecibido)


        //Aqui cogemos y rellenamos los Spinners con arrays creadas en el archivo Strings

        //Categoria
        val spinnerCategorias = findViewById<Spinner>(R.id.categoriaEditCard)
        val listaCategorias = resources.getStringArray(R.array.categorias)
        val categoriaAdapter = ArrayAdapter(this, R.layout.spinner_layout, listaCategorias)
        spinnerCategorias.adapter = categoriaAdapter

        val todasST = dbHelper.getAllSpellTraps()

        var valorCa = 0

                if( categoriaRecibido == "Magica"){
                   valorCa = 2
                }
                else{
                     valorCa = 3
                }
                spinnerCategorias.setSelection(valorCa)




        //Categoria2
        val spinnerCategorias2 = findViewById<Spinner>(R.id.categoria2EditCard)
        val listaCategoria2 = resources.getStringArray(R.array.categoria2M)
        val categorias2Adapter =
            ArrayAdapter(this@EditSTCard, R.layout.spinner_layout, listaCategoria2)
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
                    escalaTxt.visibility = if (categoria2 == "Pendulo") View.VISIBLE else View.GONE

                    if(categoria2 =="Xyz"){

                        nivelTxt.setText("Rango:")
                    }
                    else if(categoria2 == "Link"){
                        nivelTxt.setText("Link:")
                    }
                    else{
                        nivelTxt.setText("Nivel:")
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        //Atributos
        val spinnerAtributo = findViewById<Spinner>(R.id.atributoEditCard)
        val listaAtributos = resources.getStringArray(R.array.atributo)
        val atributosAdapter =
            ArrayAdapter(this@EditSTCard, R.layout.spinner_layout, listaAtributos)
        spinnerAtributo.adapter = atributosAdapter

        spinnerAtributo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedAtributo = parent.getItemAtPosition(position) as String

                    atributo = selectedAtributo
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        val spinnerTipo = findViewById<Spinner>(R.id.tipoEditCard)



        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //Hacemos que el spinner de categoria optenga un valor cuando se selecciona uno
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> {
                        categoria = "Monstruo"

                        val params = spinnerCategorias2.layoutParams
                        params.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        spinnerCategorias2.layoutParams = params

                        val params2 = spinnerAtributo.layoutParams
                        params2.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params2.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        spinnerAtributo.layoutParams = params2

                        val params3 = campoNivel.layoutParams
                        params3.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params3.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        campoNivel.layoutParams = params3

                        val params4 = campoATK.layoutParams
                        params4.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params4.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        campoATK.layoutParams = params4

                        val params5 = campoDEF.layoutParams
                        params5.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params5.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        campoDEF.layoutParams = params5

                        val params6 = categoria2Txt.layoutParams
                        params6.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params6.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        categoria2Txt.layoutParams = params6

                        val params7 = nivelTxt.layoutParams
                        params7.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params7.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        nivelTxt.layoutParams = params7

                        val params8 = ataqueTxt.layoutParams
                        params8.width = 325
                        params8.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        ataqueTxt.layoutParams = params8

                        val params9 = defensaTxt.layoutParams
                        params9.width = 325
                        params9.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        defensaTxt.layoutParams = params9

                        val params10 = atributoTxt.layoutParams
                        params10.width = LinearLayout.LayoutParams.WRAP_CONTENT
                        params10.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        atributoTxt.layoutParams = params10

                        val listaTipo = resources.getStringArray(R.array.tipoM)
                        val tipoAdapter =
                            ArrayAdapter(this@EditSTCard, R.layout.spinner_layout, listaTipo)
                        spinnerTipo.adapter = tipoAdapter

                        spinnerTipo.onItemSelectedListener =
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

                        spinnerCategorias2.layoutParams.height = 0
                        spinnerCategorias2.requestLayout()

                        spinnerAtributo.layoutParams.height = 0
                        spinnerAtributo.requestLayout()

                        campoNivel.layoutParams.height = 0
                        campoNivel.requestLayout()

                        campoATK.layoutParams.height = 0
                        campoATK.requestLayout()

                        campoDEF.layoutParams.height = 0
                        campoDEF.requestLayout()

                        campoEscala.layoutParams.height = 0
                        campoEscala.requestLayout()

                        categoria2Txt.layoutParams.height = 0
                        categoria2Txt.requestLayout()

                        nivelTxt.layoutParams.height = 0
                        nivelTxt.requestLayout()

                        ataqueTxt.layoutParams.height = 0
                        ataqueTxt.requestLayout()

                        defensaTxt.layoutParams.height = 0
                        defensaTxt.requestLayout()

                        escalaTxt.layoutParams.height = 0
                        escalaTxt.requestLayout()

                        atributoTxt.layoutParams.height = 0
                        atributoTxt.requestLayout()

                        val listaTipo = resources.getStringArray(R.array.tipoS)
                        val tipoAdapter =
                            ArrayAdapter(this@EditSTCard, R.layout.spinner_layout, listaTipo)
                        spinnerTipo.adapter = tipoAdapter

                        var valorTipo = 0


                                if( tipoRecibido == "Normal"){
                                    valorTipo = 1
                                }
                                else if (tipoRecibido == "Juego Rapido"){
                                    valorTipo = 2
                                }
                                else if (tipoRecibido == "Continua"){
                                    valorTipo = 3
                                }
                                else if (tipoRecibido == "Ritual"){
                                    valorTipo = 4
                                }
                                else if (tipoRecibido == "Equipo"){
                                    valorTipo = 5
                                }
                                else if(tipoRecibido == "Campo"){
                                    valorTipo = 6
                                }
                                spinnerTipo.setSelection(valorTipo)
                        spinnerTipo.onItemSelectedListener =
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

                        spinnerCategorias2.layoutParams.height = 0
                        spinnerCategorias2.requestLayout()

                        spinnerAtributo.layoutParams.height = 0
                        spinnerAtributo.requestLayout()

                        campoNivel.layoutParams.height = 0
                        campoNivel.requestLayout()

                        campoATK.layoutParams.height = 0
                        campoATK.requestLayout()

                        campoDEF.layoutParams.height = 0
                        campoDEF.requestLayout()

                        campoEscala.layoutParams.height = 0
                        campoEscala.requestLayout()

                        categoria2Txt.layoutParams.height = 0
                        categoria2Txt.requestLayout()

                        nivelTxt.layoutParams.height = 0
                        nivelTxt.requestLayout()

                        ataqueTxt.layoutParams.height = 0
                        ataqueTxt.requestLayout()

                        defensaTxt.layoutParams.height = 0
                        defensaTxt.requestLayout()

                        escalaTxt.layoutParams.height = 0
                        escalaTxt.requestLayout()

                        atributoTxt.layoutParams.height = 0
                        atributoTxt.requestLayout()

                        val listaTipo = resources.getStringArray(R.array.tipoT)
                        val tipoAdapter =
                            ArrayAdapter(this@EditSTCard, R.layout.spinner_layout, listaTipo)
                        spinnerTipo.adapter = tipoAdapter

                        var valorTipo = 0

                                if( tipoRecibido == "Normal"){
                                    valorTipo = 1
                                }
                                else if (tipoRecibido == "Continua"){
                                    valorTipo = 2
                                }
                                else if (tipoRecibido == "Counter"){
                                    valorTipo = 3
                                }
                                spinnerTipo.setSelection(valorTipo)

                        spinnerTipo.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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

        crear_btn.setOnClickListener {

            if(campoNivel.text.toString().isEmpty()){
                nivel = null
            }
            else{
                nivel = campoNivel.text.toString().toInt()
            }
            if(campoATK.text.toString() == ""){
                ataque = null
            }
            else{
                ataque = campoATK.text.toString().toInt()
            }
            if(campoDEF.text.toString() == ""){
                defensa = null
            }
            else{
                defensa = campoDEF.text.toString().toInt()
            }
            if(campoEscala.text.toString() == ""){
                escala = null
            }
            else{
                escala = campoEscala.text.toString().toInt()

            }
            if(campoCantidad.text.toString() == ""){
                cantidad = 1
            }
            else{
                cantidad = campoCantidad.text.toString().toInt()
            }

            nombre = campoNombre.text.toString()
            codigo = campoCodigo.text.toString()

            if(categoria == "Monstruo") {

                val databaseHelper = DatabaseHelper(this@EditSTCard)
                var lista = databaseHelper.getAllSpellTraps()

                for(spellTrap in lista){
                    if(spellTrap.id == idRecibido){
                        cambio = spellTrap.cambio
                    }
                }

                val monstruo = Monstruo(0, "Monstruo", categoria2, nombre, atributo, nivel, tipo,
                    ataque, defensa, codigo, escala, cantidad, currentPhotoPath, cambio)
                Toast.makeText(this, "Carta Editada!", Toast.LENGTH_SHORT).show()

                databaseHelper.insertMonstruo(monstruo)
                databaseHelper.deleteSpellTrap(idRecibido)
            }
            else{
                val databaseHelper = DatabaseHelper(this@EditSTCard)
                var lista = databaseHelper.getAllSpellTraps()

                for(spellTrap in lista){
                    if(spellTrap.id == idRecibido){
                        cambio = spellTrap.cambio
                    }
                }

                val spell_trap = Spells_Traps(idRecibido, nombre,categoria,tipo,codigo,cantidad,currentPhotoPath, cambio)


                if(cantidad > 0) {

                    databaseHelper.updateSpellTrap(spell_trap)
                    Toast.makeText(this, "Carta Editada!", Toast.LENGTH_SHORT).show()
                }
                else{

                    databaseHelper.deleteSpellTrap(idRecibido)
                    Toast.makeText(this, "Carta Borrada!", Toast.LENGTH_SHORT).show()

                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        foto_btn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }else{
                startCamera()
            }
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.mybinder.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startForResult.launch(takePictureIntent)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {

            currentPhotoPath = absolutePath
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val file = File(currentPhotoPath)
            val scaledBitmap = scaleImage(BitmapFactory.decodeFile(file.absolutePath), 100, 100)
            imagen?.setImageBitmap(scaledBitmap)
        }
    }
    private fun scaleImage(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var imageWidth = image.width
        var imageHeight = image.height
        val ratio = imageWidth.toFloat() / imageHeight.toFloat()

        if (imageWidth > imageHeight) {
            imageWidth = maxWidth
            imageHeight = (imageWidth / ratio).toInt()
        } else {
            imageHeight = maxHeight
            imageWidth = (imageHeight * ratio).toInt()
        }

        return Bitmap.createScaledBitmap(image, imageWidth, imageHeight, true)
    }


}