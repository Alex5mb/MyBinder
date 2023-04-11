package com.example.mybinder.controllers

import android.provider.BaseColumns

class MonstruoContract {
    companion object{
        val VERSION = 1
        class Entrada : BaseColumns{
            companion object{
                const val DATABASE_NAME = "Monstruos.db"
                const val TABLE_MONSTRUOS = "monstruos"
                const val TABLE_SPELL_TRAP = "spell_trap"
                const val COL_ID = "id"
                const val COL_CATEGORIA = "categoria"
                const val COL_CATEGORIA2 = "categoria2"
                const val COL_NOMBRE = "nombre"
                const val COL_ATRIBUTO = "atributo"
                const val COL_NIVEL = "nivel"
                const val COL_TIPO = "tipo"
                const val COL_ATAQUE = "ataque"
                const val COL_DEFENSA = "defensa"
                const val COL_CODIGO = "codigo"
                const val COL_ESCALA = "escala"
                const val COL_CANTIDAD = "cantidad"
                const val COL_IMAGEN = "imagen"
                const val COL_CAMBIO = "cambio"
            }
        }
    }
}