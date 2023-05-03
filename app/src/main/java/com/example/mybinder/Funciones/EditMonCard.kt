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
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditMonCard: AppCompatActivity() {

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
    private val REQUEST_CAMERA_PERMISSION = 1
    private var currentPhotoPath: String = ""
    private lateinit var imagenV: ImageView
    private var cambio: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_mon_card)

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

        AñadirCarta.dbmonster = DatabaseHelper(this@EditMonCard)


        val categoria2Txt = findViewById<TextView>(R.id.categoria2Text)
        val nivelTxt = findViewById<TextView>(R.id.nivelTextEMon)
        val ataqueTxt = findViewById<TextView>(R.id.atkText)
        val defensaTxt = findViewById<TextView>(R.id.deffText)
        val atributoTxt = findViewById<TextView>(R.id.atributoTxt)
        val escalaTxt = findViewById<TextView>(R.id.escalaTxtEMon)
        val crear_btn = findViewById<Button>(R.id.editMon)
        val foto_btn = findViewById<Button>(R.id.imagen_btnEditMon)
            imagenV = findViewById(R.id.imageViewEditMonCard)


        if (imagenRec != null) {
            currentPhotoPath = imagenRec
            Glide.with(this)
                .load(imagenRec)
                .into(imagenV)
        }




        //Campos TextView
        val campoNombre = findViewById<EditText>(R.id.nombreEditMonCard)
        val campoNivel = findViewById<EditText>(R.id.nivelEditMonCard)
        val campoATK = findViewById<EditText>(R.id.atackEditMonCard)
        val campoDEF = findViewById<EditText>(R.id.deffEditMonCard)
        val campoEscala = findViewById<EditText>(R.id.escalaEditMonCard)
        val campoCantidad = findViewById<EditText>(R.id.cantidadEditMonCard)
        val campoCodigo = findViewById<EditText>(R.id.codigoEditMonCard)


        campoATK.setText(ataqueRec.toString())
        campoDEF.setText(defensaRec.toString())
        campoEscala.setText(escalaRec.toString())
        campoNivel.setText(nivelRec.toString())
        campoNombre.setText(nombreRec)
        campoCantidad.setText(cantidadRec.toString())
        campoCodigo.setText(codigoRec)


        //Aqui cogemos y rellenamos los Spinners con arrays creadas en el archivo Strings

        //Categoria
        val spinnerCategorias = findViewById<Spinner>(R.id.categoriaEditMonCard)
        val listaCategorias = resources.getStringArray(R.array.categorias)
        val categoriaAdapter = ArrayAdapter(this, R.layout.spinner_layout, listaCategorias)
        spinnerCategorias.adapter = categoriaAdapter

        var valorCategoria = 0

        if( categoriaRec == "Magica"){
            valorCategoria = 2
        }
        else if(categoriaRec == "Trampa"){
            valorCategoria = 3
        }
        else if(categoriaRec =="Monstruo"){
            valorCategoria = 1
        }
        else{
            valorCategoria = 0
        }
        spinnerCategorias.setSelection(valorCategoria)

        //Categoria2
        val spinnerCategorias2 = findViewById<Spinner>(R.id.categoria2EditMonCard)
        val listaCategoria2 = resources.getStringArray(R.array.categoria2M)
        val categorias2Adapter =
            ArrayAdapter(this@EditMonCard, R.layout.spinner_layout, listaCategoria2)
        spinnerCategorias2.adapter = categorias2Adapter

        var valorCategoria2 = 0

        if( categoria2Rec == "Normal"){
            valorCategoria2 = 1
        }
        else if(categoria2Rec == "Efecto"){
            valorCategoria2 = 2
        }
        else if(categoria2Rec == "Fusion"){
            valorCategoria2 = 3
        }
        else if(categoria2Rec == "Ritual"){
            valorCategoria2 = 4
        }
        else if(categoria2Rec == "Sincronia"){
            valorCategoria2 = 5
        }
        else if(categoria2Rec == "Xyz"){
            valorCategoria2 = 6
        }
        else if(categoria2Rec == "Pendulo"){
            valorCategoria2 = 7
        }
        else{
            valorCategoria2 = 8
        }
        spinnerCategorias2.setSelection(valorCategoria2)



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

                        nivelTxt.text = "Rango:"
                    }
                    else if(categoria2 == "Link"){
                        nivelTxt.text = "Link:"
                    }
                    else{
                        nivelTxt.text = "Nivel:"
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        //Atributos
        val spinnerAtributo = findViewById<Spinner>(R.id.atributoEditMonCard)
        val listaAtributos = resources.getStringArray(R.array.atributo)
        val atributosAdapter =
            ArrayAdapter(this@EditMonCard, R.layout.spinner_layout, listaAtributos)
        spinnerAtributo.adapter = atributosAdapter

        var valorAtributo = 0

        if( atributoRec == "Agua"){
            valorAtributo = 1
        }
        else if(atributoRec == "Tierra"){
            valorAtributo = 2
        }
        else if(atributoRec == "Fuego"){
            valorAtributo = 3
        }
        else if(atributoRec == "Viento"){
            valorAtributo = 4
        }
        else if(atributoRec == "Oscuridad"){
            valorAtributo = 5
        }
        else if(atributoRec == "Luz"){
            valorAtributo = 6
        }
        else if(atributoRec == "Divinidad"){
            valorAtributo = 7
        }
        spinnerAtributo.setSelection(valorAtributo)


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

        val spinnerTipo = findViewById<Spinner>(R.id.tipoEditMonCard)



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
                            ArrayAdapter(this@EditMonCard, R.layout.spinner_layout, listaTipo)
                        spinnerTipo.adapter = tipoAdapter

                        var valorTipo: Int


                        if( tipoRec == "Aqua"){
                            valorTipo = 1
                        }
                        else if(tipoRec == "Bestia"){
                            valorTipo = 2
                        }
                        else if(tipoRec == "Bestia-Alada"){
                            valorTipo = 3
                        }
                        else if(tipoRec == "Bestia Divina"){
                            valorTipo = 4
                        }
                        else if(tipoRec == "Bestia-guerrero"){
                            valorTipo = 5
                        }
                        else if(tipoRec == "Ciberso"){
                            valorTipo = 6
                        }
                        else if(tipoRec == "Demonio"){
                            valorTipo = 7
                        }
                        else if(tipoRec == "Dragón"){
                            valorTipo =8

                        }
                        else if(tipoRec == "Dinosurio"){
                            valorTipo = 9
                        }
                        else if(tipoRec == "Guerrero"){
                            valorTipo = 10
                        }
                        else if(tipoRec == "Hada"){
                            valorTipo = 11
                        }
                        else if(tipoRec == "Insecto"){
                            valorTipo = 12
                        }
                        else if(tipoRec == "Lanzador de conjuros"){
                            valorTipo = 13
                        }
                        else if(tipoRec == "Maquina"){
                            valorTipo = 14
                        }
                        else if(tipoRec == "Pez"){
                            valorTipo = 15
                        }
                        else if(tipoRec == "Planta"){
                            valorTipo = 16
                        }
                        else if(tipoRec == "Pyro"){
                            valorTipo = 17
                        }
                        else if(tipoRec == "Psíquico"){
                            valorTipo = 18
                        }
                        else if(tipoRec == "Reptil"){
                            valorTipo = 19
                        }
                        else if(tipoRec == "Roca"){
                            valorTipo = 20
                        }
                        else if(tipoRec == "Serpiente marina"){
                            valorTipo = 21
                        }
                        else if(tipoRec == "Trueno"){
                            valorTipo = 22
                        }
                        else if(tipoRec == "Wyrm"){
                            valorTipo = 23
                        }
                        else{
                            valorTipo = 24
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
                            ArrayAdapter(this@EditMonCard, R.layout.spinner_layout, listaTipo)
                        spinnerTipo.adapter = tipoAdapter

                        var valorTipo = 0


                        if( tipoRec == "Normal"){
                            valorTipo = 1
                        }
                        else if (tipoRec == "Juego Rapido"){
                            valorTipo = 2
                        }
                        else if (tipoRec == "Continua"){
                            valorTipo = 3
                        }
                        else if (tipoRec == "Ritual"){
                            valorTipo = 4
                        }
                        else if (tipoRec == "Equipo"){
                            valorTipo = 5
                        }
                        else if(tipoRec == "Campo"){
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
                            ArrayAdapter(this@EditMonCard, R.layout.spinner_layout, listaTipo)
                        spinnerTipo.adapter = tipoAdapter

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

                val databaseHelper = DatabaseHelper(this@EditMonCard)

                var lista = databaseHelper.getAllMonstruos()

                for(monstruo in lista){
                   if(monstruo.id == idRec){
                       cambio = monstruo.cambio
                   }
                }

                val monstruo = Monstruo(idRec, "Monstruo", categoria2, nombre, atributo, nivel, tipo,
                    ataque, defensa, codigo, escala, cantidad, currentPhotoPath, cambio)
                Toast.makeText(this, "Carta Editada!", Toast.LENGTH_SHORT).show()


                if(cantidad > 0) {

                    databaseHelper.updateMonstruo(monstruo)
                    Toast.makeText(this, "Carta Editada!", Toast.LENGTH_SHORT).show()
                }
                else{

                    databaseHelper.deleteMonstruo(idRec)
                    Toast.makeText(this, "Carta Borrada!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                val databaseHelper = DatabaseHelper(this@EditMonCard)

                var lista = databaseHelper.getAllMonstruos()

                for(monstruo in lista){
                    if(monstruo.id == idRec){
                        cambio = monstruo.cambio
                    }
                }
                val spell_trap = Spells_Traps(0, nombre,categoria,tipo,codigo,cantidad,currentPhotoPath, cambio)

                if(cantidad > 0) {

                    databaseHelper.deleteMonstruo(idRec)
                    databaseHelper.updateSpellTrap(spell_trap)
                    Toast.makeText(this, "Carta Editada!", Toast.LENGTH_SHORT).show()
                }
                else{

                    databaseHelper.deleteSpellTrap(idRec)
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
            Glide.with(this)
                .load(scaledBitmap)
                .into(imagenV)
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

