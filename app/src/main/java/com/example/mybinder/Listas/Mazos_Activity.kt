package com.example.mybinder.Listas

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mybinder.Adapters.MazoAdapter
import com.example.mybinder.Funciones.Crear_Mazo_Activity
import com.example.mybinder.Funciones.AñadirCarta
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper
import com.google.android.material.navigation.NavigationView


class Mazos_Activity: AppCompatActivity()  {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selector_deck)

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

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val databaseHelper = DatabaseHelper(this)

        val nuevoD_btn  = findViewById<Button>(R.id.newDeck_btn)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAllDecks)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        val adapter = MazoAdapter(databaseHelper.gerAllDecks())

        recyclerView.adapter = adapter


        nuevoD_btn.setOnClickListener{
            val intent = Intent(this, Crear_Mazo_Activity::class.java)
            startActivity(intent)
        }

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
            }
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}