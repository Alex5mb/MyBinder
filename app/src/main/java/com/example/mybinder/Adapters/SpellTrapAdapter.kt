package com.example.mybinder.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.R
import com.example.mybinder.controllers.OnItemClickListener


class SpellTrapAdapter(private val spellTraps: List<Spells_Traps>, private var listener: OnItemClickListener) :
    RecyclerView.Adapter<SpellTrapAdapter.SpellTrapViewHolder>() {

    fun updateList(newList: List<Spells_Traps>) {
        spellTraps.toMutableList().clear()
        spellTraps.toMutableList().addAll(newList)
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellTrapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spell_card, parent, false)
        return SpellTrapViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpellTrapViewHolder, position: Int) {
        val spellTrap = spellTraps[position]

        val spellCardView = holder.itemView.findViewById<CardView>(R.id.sp_cardView)

        val imageView = spellCardView.findViewById<ImageView>(R.id.imageViewSp)

        Glide.with(spellCardView)
            .load(spellTrap.imagen)
            .into(imageView)

        val nombre = spellCardView.findViewById<TextView>(R.id.nombreSp)
        nombre.text = spellTrap.nombre

        val tipo = spellCardView.findViewById<TextView>(R.id.tipoSp)
        tipo.text = spellTrap.tipo

        val cantidad = spellCardView.findViewById<TextView>(R.id.cantidadSp)
        cantidad.text = spellTrap.cantidad.toString()

        val codigo = spellCardView.findViewById<TextView>(R.id.codigoSp)
        codigo.text = spellTrap.codigo

        val categoria = spellCardView.findViewById<TextView>(R.id.categoriaSp)
        categoria.text = spellTrap.categoria

        if(spellTrap.categoria == "Trampa") {
            spellCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.trampas))
        } else {
            spellCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.magicas))
        }

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return spellTraps.size
    }

    class SpellTrapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.nombreSp)
        val tvImagen: ImageView = itemView.findViewById(R.id.imageViewSp)
        val tvTipo: TextView = itemView.findViewById(R.id.tipoSp)
        val tvcantidad: TextView = itemView.findViewById(R.id.cantidadSp)
        val tvCategoria: TextView = itemView.findViewById(R.id.categoriaSp)
        val tvCodigo: TextView = itemView.findViewById(R.id.codigoSp)
        val cardView: CardView = itemView.findViewById(R.id.sp_cardView)
    }
}

