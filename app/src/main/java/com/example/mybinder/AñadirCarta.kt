package com.example.mybinder

import android.app.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.Manifest
import android.annotation.SuppressLint
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
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.controllers.DatabaseHelper
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AñadirCarta : AppCompatActivity() {

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
    private lateinit var currentPhotoPath: String

    companion object{
        lateinit var dbmonster: DatabaseHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anhadircarta)

        currentPhotoPath = ""

        dbmonster = DatabaseHelper(this@AñadirCarta)

        val categoria2Txt = findViewById<TextView>(R.id.categoria2Text)
        val nivelTxt = findViewById<TextView>(R.id.nivelTextEMon)
        val ataqueTxt = findViewById<TextView>(R.id.atkText)
        val defensaTxt = findViewById<TextView>(R.id.deffText)
        val atributoTxt = findViewById<TextView>(R.id.atributoTxt)
        val escalaTxt = findViewById<TextView>(R.id.escalaTxtEMon)
        val crear_btn = findViewById<Button>(R.id.crear)
        val foto_btn = findViewById<Button>(R.id.imagen_btn)

        //Campos TextView
        val campoNombre = findViewById<EditText>(R.id.nombreNewCard)
        val campoNivel = findViewById<EditText>(R.id.nivelNewCard)
        val campoATK = findViewById<EditText>(R.id.atackNewCard)
        val campoDEF = findViewById<EditText>(R.id.deffNewCard)
        val campoEscala = findViewById<EditText>(R.id.escalaNewCard)
        val campoCantidad = findViewById<EditText>(R.id.cantidadNewCard)
        val campoCodigo = findViewById<EditText>(R.id.codigoNewCard)
        imagen = findViewById(R.id.imageViewNewCard)


        //Aqui cogemos y rellenamos los Spinners con arrays creadas en el archivo Strings

        //Categoria
        val spinnerCategorias = findViewById<Spinner>(R.id.categoriaNewCard)
        val listaCategorias = resources.getStringArray(R.array.categorias)
        val categoriaAdapter = ArrayAdapter(this, R.layout.spinner_layout, listaCategorias)
        spinnerCategorias.adapter = categoriaAdapter

        //Categoria2
        val spinnerCategorias2 = findViewById<Spinner>(R.id.categoria2NewCard)
        val listaCategoria2 = resources.getStringArray(R.array.categoria2M)
        val categorias2Adapter =
            ArrayAdapter(this@AñadirCarta, R.layout.spinner_layout, listaCategoria2)
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
        val spinnerAtributo = findViewById<Spinner>(R.id.atributoNewCard)
        val listaAtributos = resources.getStringArray(R.array.atributo)
        val atributosAdapter =
            ArrayAdapter(this@AñadirCarta, R.layout.spinner_layout, listaAtributos)
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

        val spinnerTipo = findViewById<Spinner>(R.id.tipoNewCard)

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
                        categoria = "Monstruo"

                        spinnerCategorias2.visibility = View.VISIBLE
                        spinnerAtributo.visibility = View.VISIBLE
                        campoNivel.visibility = View.VISIBLE
                        campoATK.visibility = View.VISIBLE
                        campoDEF.visibility = View.VISIBLE
                        categoria2Txt.visibility = View.VISIBLE
                        nivelTxt.visibility = View.VISIBLE
                        ataqueTxt.visibility = View.VISIBLE
                        defensaTxt.visibility = View.VISIBLE
                        atributoTxt.visibility = View.VISIBLE

                        val listaTipo = resources.getStringArray(R.array.tipoM)
                        val tipoAdapter =
                            ArrayAdapter(this@AñadirCarta, R.layout.spinner_layout, listaTipo)
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
                    1 -> {
                        categoria = "Magica"

                        spinnerCategorias2.visibility = View.INVISIBLE
                        spinnerAtributo.visibility = View.INVISIBLE
                        campoNivel.visibility = View.INVISIBLE
                        campoATK.visibility = View.INVISIBLE
                        campoDEF.visibility = View.INVISIBLE
                        campoEscala.visibility = View.INVISIBLE
                        categoria2Txt.visibility = View.INVISIBLE
                        nivelTxt.visibility = View.INVISIBLE
                        ataqueTxt.visibility = View.INVISIBLE
                        defensaTxt.visibility = View.INVISIBLE
                        escalaTxt.visibility = View.INVISIBLE
                        atributoTxt.visibility = View.INVISIBLE

                        val listaTipo = resources.getStringArray(R.array.tipoS)
                        val tipoAdapter =
                            ArrayAdapter(this@AñadirCarta, R.layout.spinner_layout, listaTipo)
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
                        categoria = "Trampa"

                        spinnerCategorias2.visibility = View.INVISIBLE
                        spinnerAtributo.visibility = View.INVISIBLE
                        campoNivel.visibility = View.INVISIBLE
                        campoATK.visibility = View.INVISIBLE
                        campoDEF.visibility = View.INVISIBLE
                        campoEscala.visibility = View.INVISIBLE
                        categoria2Txt.visibility = View.INVISIBLE
                        nivelTxt.visibility = View.INVISIBLE
                        ataqueTxt.visibility = View.INVISIBLE
                        defensaTxt.visibility = View.INVISIBLE
                        escalaTxt.visibility = View.INVISIBLE
                        atributoTxt.visibility = View.INVISIBLE

                        val listaTipo = resources.getStringArray(R.array.tipoT)
                        val tipoAdapter =
                            ArrayAdapter(this@AñadirCarta, R.layout.spinner_layout, listaTipo)
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

                val categoriaNonNull = categoria ?: "Monstruo"
                val monstruo = Monstruo(0, categoriaNonNull, categoria2, nombre, atributo, nivel, tipo,
                    ataque, defensa, codigo, escala, cantidad, currentPhotoPath)
                Toast.makeText(this, "Monstruo Creado!", Toast.LENGTH_SHORT).show()

                val imagePath: String = currentPhotoPath
                val databaseHelper = DatabaseHelper(this@AñadirCarta)
                databaseHelper.insertImagePath(imagePath)

                databaseHelper.insertMonstruo(monstruo)
            }
            else{
                val spell_trap = Spells_Traps(0, nombre,categoria,tipo,codigo,cantidad,currentPhotoPath)

                if(categoria == "Magica"){
                    Toast.makeText(this, "Magica Creada!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Trampa Creada!", Toast.LENGTH_SHORT).show()
                }

                val imagePath: String = currentPhotoPath
                val databaseHelper = DatabaseHelper(this@AñadirCarta)
                databaseHelper.insertImagePath(imagePath)

                databaseHelper.insertSpellTrap(spell_trap)
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
