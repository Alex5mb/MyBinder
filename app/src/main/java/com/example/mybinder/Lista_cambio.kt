package com.example.mybinder

import android.os.Environment
import android.util.Log
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybinder.Adapters.MonstruoAdapter
import com.example.mybinder.Adapters.SpellTrapAdapter
import com.example.mybinder.controllers.DatabaseHelper
import com.example.mybinder.controllers.OnItemClickListener
import com.google.android.material.navigation.NavigationView


class Lista_cambio: AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_cambio_layout)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val databaseHelper = DatabaseHelper(this@Lista_cambio)

        val filtrado_btn = findViewById<ImageButton>(R.id.filtrado_Main)
        val pdf_btn = findViewById<ImageButton>(R.id.pdf_btn)

        // Crear un RecyclerView para la lista de monstruos

        val monstruosRecyclerView = RecyclerView(this)
        val monstruosLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        monstruosRecyclerView.layoutManager = monstruosLayoutManager
        val monstruosList = databaseHelper.getMonstruosConCambio()
        val monstruosAdapter =
            if (monstruosList != null) MonstruoAdapter(monstruosList,object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(applicationContext, DetallesCartaMon::class.java)

                    intent.putExtra("id", monstruosList[position].id)
                    intent.putExtra("categoria", monstruosList[position].categoria)
                    intent.putExtra("categoria2",monstruosList[position].categoria2 )
                    intent.putExtra("nombre", monstruosList[position].nombre)
                    intent.putExtra("atributo",monstruosList[position].atributo)
                    intent.putExtra("nivel",monstruosList[position].nivel)
                    intent.putExtra("tipo", monstruosList[position].tipo)
                    intent.putExtra("ataque",monstruosList[position].ataque)
                    intent.putExtra("defensa",monstruosList[position].defensa)
                    intent.putExtra("codigo", monstruosList[position].codigo)
                    intent.putExtra("escala", monstruosList[position].escala)
                    intent.putExtra("cantidad", monstruosList[position].cantidad)
                    intent.putExtra("imagen", monstruosList[position].imagen)
                    intent.putExtra("cambio", monstruosList[position].cambio)


                    startActivity(intent)
                }


            }) else MonstruoAdapter(emptyList(), object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                }

            })
        monstruosRecyclerView.adapter = monstruosAdapter

        // Crear un RecyclerView para la lista de spells y traps

        val spellsTrapsRecyclerView = RecyclerView(this)
        val spellsTrapsLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        spellsTrapsRecyclerView.layoutManager = spellsTrapsLayoutManager

        val spellsTrapsList = databaseHelper.getSpellTrapsWithChanges()
        val spellsTrapsAdapter = SpellTrapAdapter(spellsTrapsList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(applicationContext, DetallesCartaST::class.java)

                intent.putExtra("id", spellsTrapsList[position].id)
                intent.putExtra("imagen", spellsTrapsList[position].imagen)
                intent.putExtra("tipo", spellsTrapsList[position].tipo)
                intent.putExtra("codigo", spellsTrapsList[position].codigo)
                intent.putExtra("categoria", spellsTrapsList[position].categoria)
                intent.putExtra("cantidad", spellsTrapsList[position].cantidad)
                intent.putExtra("nombre", spellsTrapsList[position].nombre)
                intent.putExtra("cambio",  spellsTrapsList[position].cambio)

                startActivity(intent)
            }

        })
        spellsTrapsRecyclerView.adapter = spellsTrapsAdapter
        spellsTrapsAdapter.notifyDataSetChanged()

        val layout2 = findViewById<RecyclerView>(R.id.contenedorRecyclerMonster)
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layout2.layoutManager = layoutManager2
        layout2.adapter = monstruosAdapter

        val layout = findViewById<RecyclerView>(R.id.contenedorRecyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layout.layoutManager = layoutManager
        layout.adapter = spellsTrapsAdapter

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.Lista -> {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.add -> {

                    val intent = Intent(this, AñadirCarta::class.java)
                    startActivity(intent)
                }

                R.id.mazos -> {
                    val intent = Intent(this, Mazos_Activity::class.java)
                    startActivity(intent)
                }

                R.id.cambio -> {

                    val intent = Intent(this, Lista_cambio::class.java)
                    startActivity(intent)
                }
                R.id.probabilidades -> {

                }
            }
            true

        }

        filtrado_btn.setOnClickListener{
            val intent = Intent(this, Filtrado_activity::class.java)
            startActivity(intent)
        }

        pdf_btn.setOnClickListener{

           val listacompleta = mutableListOf<Any>( monstruosList + spellsTrapsList )

            createPdf(listacompleta)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun createPdf(objects: List<Any>) {
        val document = Document()
        val fileName = "mi cambio.pdf"
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/" + fileName

        val file = File(filePath)
        file.createNewFile()
        val outputStream = FileOutputStream(file, false)

        PdfWriter.getInstance(document, outputStream)
        document.open()

        val titleFont = Font(Font.FontFamily.TIMES_ROMAN, 18f, Font.BOLD)
        val bodyFont = Font(Font.FontFamily.TIMES_ROMAN, 12f)

        document.add(Paragraph("Cambio:", titleFont))

        for (obj in objects) {
            val objString = obj.toString()
            val paragraph = Paragraph(objString, bodyFont)
            paragraph.alignment = Element.ALIGN_JUSTIFIED
            document.add(paragraph)
        }

        document.close()
        outputStream.close()
        Log.d("PDF", "PDF created at $filePath")
    }

}
