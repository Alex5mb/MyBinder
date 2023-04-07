package com.example.mybinder.Adapters

import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.R
import com.bumptech.glide.Glide

class MyAdapter( private val monstruos: List<Monstruo>, private val spellTraps: List<Spells_Traps>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MONSTRUO_VIEW_TYPE = 0
    private val SPELL_TRAP_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MONSTRUO_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.monster_card, parent, false)
                MonstruoAdapter.MonstruoViewHolder(view)
            }
            SPELL_TRAP_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.spell_card, parent, false)
                SpellTrapAdapter.SpellTrapViewHolder(view)
            }
            else -> throw IllegalArgumentException("Tipo Invalido")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MONSTRUO_VIEW_TYPE -> {
                val monstruo = monstruos[position]
            }
            SPELL_TRAP_VIEW_TYPE -> {
                val spellTrap = spellTraps[position - monstruos.size]

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

            }
        }
    }

    override fun getItemCount() = monstruos.size + spellTraps.size

    override fun getItemViewType(position: Int): Int {
        return if (position < monstruos.size) {
            MONSTRUO_VIEW_TYPE
        } else {
            SPELL_TRAP_VIEW_TYPE
        }
    }
    }