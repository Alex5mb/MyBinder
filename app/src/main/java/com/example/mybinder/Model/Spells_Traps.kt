package com.example.mybinder.Model

import android.widget.ImageView

class Spells_Traps(
    val id: Int?,
    val nombre: String,
    val categoria: String,
    val tipo: String?,
    val codigo: String?,
    val cantidad: Int?,
    val imagen: String?
) {
    override fun toString(): String {
        return "$nombre\n" +
                "Categoría: $categoria\n" +
                "Tipo: $tipo\n" +
                "Código: $codigo\n"+
                "Cantidad: $cantidad\n"
    }
}
