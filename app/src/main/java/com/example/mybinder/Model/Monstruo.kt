package com.example.mybinder.Model

class Monstruo(
    val id: Int?,
    val categoria:String,
    val categoria2:String,
    val nombre: String,
    val atributo: String?,
    val nivel: Int?,
    val tipo: String?,
    val ataque: Int?,
    val defensa: Int?,
    val codigo: String?,
    val escala: Int?,
    val cantidad: Int?,
    val imagen: String?,
    val cambio: Boolean
) {
    override fun toString(): String {
        return "Cantidad: $cantidad" + " -- " + "Nombre: $nombre" + " -- " + "Codigo: $codigo\n"
    }
}