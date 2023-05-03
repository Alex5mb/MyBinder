package com.example.mybinder.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybinder.Model.Carta
import com.example.mybinder.R
import com.example.mybinder.controllers.OnItemClickListener

class ProbHandAdapter (private var mano: MutableList<Carta>, param: OnItemClickListener) : RecyclerView.Adapter<ProbHandAdapter.ViewHolder>() {

    private var cantidad: TextView? = null


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {

            cantidad = itemView.findViewById(R.id.cantidadDeck)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carta_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mano.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mazo = mano[position]

        val cardView = holder.itemView.findViewById<CardView>(R.id.image_card)

        val imageView = cardView.findViewById<ImageView>(R.id.cartaImageMazo)

        Glide.with(holder.itemView)
            .load(mazo.imagen)
            .into(imageView)

    }

}
