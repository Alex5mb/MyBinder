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
    val imagen: String?
) {
    override fun toString(): String {
        return "$nombre (ID: $id)\n" +
                "Categoria: $categoria\n" +
                "Categoria2: $categoria2\n" +
                "Atributo: $atributo\n" +
                "Nivel: $nivel\n" +
                "Tipo: $tipo\n" +
                "Ataque: $ataque\n" +
                "Defensa: $defensa\n" +
                "Código: $codigo" +
                "Escala: $escala" +
                "Cantidad: $cantidad"
    }
}