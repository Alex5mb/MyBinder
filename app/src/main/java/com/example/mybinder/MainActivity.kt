package com.example.mybinder


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybinder.Adapters.MonstruoAdapter
import com.example.mybinder.Adapters.SpellTrapAdapter

import com.example.mybinder.controllers.DatabaseHelper
import com.example.mybinder.controllers.OnItemClickListener
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val databaseHelper = DatabaseHelper(this@MainActivity)

        // Crear un RecyclerView para la lista de monstruos

        val monstruosRecyclerView = RecyclerView(this)
        val monstruosLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        monstruosRecyclerView.layoutManager = monstruosLayoutManager
        val monstruosList = databaseHelper.getAllMonstruos()
        val monstruosAdapter =
            if (monstruosList != null) MonstruoAdapter(monstruosList,object : OnItemClickListener{
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


                    startActivity(intent)
                }


            }) else MonstruoAdapter(emptyList(), object : OnItemClickListener{
                override fun onItemClick(position: Int) {
                }

            })
        monstruosRecyclerView.adapter = monstruosAdapter

        // Crear un RecyclerView para la lista de spells y traps

        val spellsTrapsRecyclerView = RecyclerView(this)
        val spellsTrapsLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        spellsTrapsRecyclerView.layoutManager = spellsTrapsLayoutManager

        val spellsTrapsList = databaseHelper.getAllSpellTraps()
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

                }

                R.id.cambio -> {

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
