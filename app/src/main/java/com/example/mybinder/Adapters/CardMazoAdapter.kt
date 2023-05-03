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

class CardMazoAdapter(private var mainDeck: MutableList<Carta>, param: OnItemClickListener) : RecyclerView.Adapter<CardMazoAdapter.ViewHolder>() {

    private var cantidad: TextView? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
            cantidad = itemView.findViewById(R.id.cantidadDeck)
        }


        override fun onClick(v: View?) {

           /* val databaseHelper = DatabaseHelper(itemView.context)
            databaseHelper.deleteTablaIntermedia(mainDeck[position].id)
            println(mainDeck)
            notifyDataSetChanged()*/

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                mainDeck.removeAt(position)
                notifyItemChanged(position)
                notifyItemRemoved(position)
                cantidad?.text = mainDeck.size.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carta_cardview, parent, false)
        return ViewHolder(view)
    }

        override fun getItemCount(): Int {
           return mainDeck.size
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mazo = mainDeck[position]

        val cardView = holder.itemView.findViewById<CardView>(R.id.image_card)

        val imageView = cardView.findViewById<ImageView>(R.id.cartaImageMazo)

        Glide.with(holder.itemView)
            .load(mazo.imagen)
            .into(imageView)

    }

}