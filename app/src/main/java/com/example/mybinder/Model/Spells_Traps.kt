package com.example.mybinder.Model

import android.widget.ImageView

class Spells_Traps(
    val id: Int?,
    val nombre: String?,
    val categoria: String?,
    val tipo: String?,
    val codigo: String?,
    val cantidad: Int?,
    val imagen: String?,
    val cambio: Boolean
) {
    override fun toString(): String {
        return "Cantidad: $cantidad" + ", " + "Nombre: $nombre" + ", "+ "Codigo: $codigo\n"
    }
}
