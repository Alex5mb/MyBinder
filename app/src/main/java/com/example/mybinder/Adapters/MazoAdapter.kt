package com.example.mybinder.Adapters

import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybinder.Funciones.Editar_Mazo_Activity
import com.example.mybinder.Model.Mazo
import com.example.mybinder.Funciones.Probabilidad_Activity
import com.example.mybinder.R
import com.example.mybinder.controllers.DatabaseHelper

class MazoAdapter(private var mazoList: List<Mazo>) : RecyclerView.Adapter<MazoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener, View.OnClickListener {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombre_mazo)
        val fotoImageView: ImageView = itemView.findViewById(R.id.imagen_mazo)

        init {
            itemView.setOnCreateContextMenuListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            // Crear menú de contexto y agregar elementos
            val inflater = MenuInflater(itemView.context)
            inflater.inflate(R.menu.menucontextual, menu)

            menu?.findItem(R.id.editar)?.setOnMenuItemClickListener {

                val intent = Intent(itemView.context, Editar_Mazo_Activity::class.java)
                intent.putExtra("id", mazoList[position].id)
                intent.putExtra("nombre", mazoList[position].nombre)
                itemView.context.startActivity(intent)
                true
            }

            // Establecer el OnMenuItemClickListener para cada elemento del menú
            menu?.findItem(R.id.eliminar)?.setOnMenuItemClickListener {
                val databaseHelper = DatabaseHelper(itemView.context)
                databaseHelper.deleteDeck(mazoList[position].id)
                databaseHelper.deleteAllCardDeck(mazoList[position].id)
                Toast.makeText(itemView.context, "Eliminar mazo en la posición $position", Toast.LENGTH_SHORT).show()

                val mutableList = mazoList.toMutableList()
                mutableList.removeAt(position)
                mazoList = mutableList.toList()

                notifyItemRemoved(position)
                notifyDataSetChanged()
                true
            }
            menu?.findItem(R.id.probabilidades)?.setOnMenuItemClickListener {

                val intent = Intent(itemView.context, Probabilidad_Activity::class.java)

                intent.putExtra("id", mazoList[position].id)
                intent.putExtra("nombre", mazoList[position].nombre)

                itemView.context.startActivity(intent)
                true
            }
        }

        override fun onClick(v: View?) {
            v?.showContextMenu()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mazo = mazoList[position]
        holder.nombreTextView.text = mazo.nombre
        Glide.with(holder.itemView)
            .load(R.drawable.unnamed)
            .into(holder.fotoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mazo_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mazoList.size
    }
}
