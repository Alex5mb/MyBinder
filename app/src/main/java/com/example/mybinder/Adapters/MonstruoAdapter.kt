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
import com.example.mybinder.R
import com.example.mybinder.controllers.OnItemClickListener

class MonstruoAdapter (private val monstruos: List<Monstruo>,private var listener: OnItemClickListener) :
    RecyclerView.Adapter<MonstruoAdapter.MonstruoViewHolder>() {

    fun updateList(newList: List<Monstruo>) {
        monstruos.toMutableList().clear()
        monstruos.toMutableList().addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonstruoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.monster_card, parent, false)
        return MonstruoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonstruoViewHolder, position: Int) {
        val monstruo = monstruos[position]

        val monsterCardView = holder.itemView.findViewById<CardView>(R.id.monsters_card)

        val imageView = monsterCardView.findViewById<ImageView>(R.id.imageViewMo)

        Glide.with(monsterCardView)
            .load(monstruo.imagen)
            .into(imageView)

        val nombre = monsterCardView.findViewById<TextView>(R.id.nombreMo)
        nombre.text = monstruo.nombre

        val tipo = monsterCardView.findViewById<TextView>(R.id.tipoMo)
        tipo.text = monstruo.tipo

        val nivel = monsterCardView.findViewById<TextView>(R.id.nivel)
        nivel.text = monstruo.nivel.toString()

        val ataque = monsterCardView.findViewById<TextView>(R.id.ataque)
        ataque.text = monstruo.ataque.toString()

        val defenda = monsterCardView.findViewById<TextView>(R.id.defensa)
        defenda.text = monstruo.defensa.toString()

        val categoria = monsterCardView.findViewById<TextView>(R.id.categoriaMo)
        categoria.text = monstruo.categoria2

        val codigo = monsterCardView.findViewById<TextView>(R.id.codigoMo)
        codigo.text = monstruo.codigo

        val cantidad = monsterCardView.findViewById<TextView>(R.id.cantidadMo)
        cantidad.text = monstruo.cantidad.toString()

        val atributo = monsterCardView.findViewById<TextView>(R.id.atributo)
        atributo.text = monstruo.atributo

        var palo = monsterCardView.findViewById<TextView>(R.id.palo)

        if(monstruo.categoria2 == "Normal"){

            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.normal))
            nombre.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            tipo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            nivel.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            ataque.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            defenda.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            categoria.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            codigo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            cantidad.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            atributo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            palo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
        else if(monstruo.categoria2 == "Efecto" || monstruo.categoria2 == "Pendulo"){

            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.efecto))
        }
        else if(monstruo.categoria2 == "Fusion"){

            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.fusion))
        }
        else if(monstruo.categoria2 =="Ritual"){
            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.ritual))
        }
        else if(monstruo.categoria2 == "Sincronia"){

            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
            nombre.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            tipo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            nivel.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            ataque.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            defenda.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            categoria.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            codigo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            cantidad.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            atributo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            palo.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))

        }
        else if(monstruo.categoria2 == "Xyz"){

            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.xyz))
        }
        else{

            monsterCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.links))
        }
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }

    }

    override fun getItemCount():Int {
        return monstruos.size
    }

     class MonstruoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvNombre: TextView
        var tvImagen: ImageView
        var tvcantidad: TextView
        var cardView: CardView

        init{
            this.tvNombre= itemView.findViewById(R.id.nombreMo)
            this.tvImagen = itemView.findViewById(R.id.imageViewMo)
            this.tvcantidad = itemView.findViewById(R.id.cantidadMo)
            this.cardView = itemView.findViewById<CardView>(R.id.monsters_card)
        }
    }
}
