package com.example.mybinder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class Mazos_Activity: AppCompatActivity()  {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selector_deck)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}