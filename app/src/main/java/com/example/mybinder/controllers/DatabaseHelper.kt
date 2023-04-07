package com.example.mybinder.controllers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ATAQUE
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ATRIBUTO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CANTIDAD
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CATEGORIA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CATEGORIA2
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CODIGO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_DEFENSA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ESCALA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ID
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_IMAGEN
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_NIVEL
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_NOMBRE
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_TIPO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.DATABASE_NAME
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.TABLE_MONSTRUOS
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.TABLE_SPELL_TRAP




class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, MonstruoContract.VERSION){

    companion object {

                val createTableMonstruos = """
            CREATE TABLE $TABLE_MONSTRUOS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_CATEGORIA TEXT,
                $COL_CATEGORIA2 TEXT,
                $COL_NOMBRE TEXT,
                $COL_ATRIBUTO TEXT,
                $COL_NIVEL INTEGER,  
                $COL_TIPO TEXT,
                $COL_ATAQUE INTEGER,
                $COL_DEFENSA INTEGER,
                $COL_CODIGO TEXT,
                $COL_ESCALA INTEGER,
                $COL_CANTIDAD INTEGER,
                $COL_IMAGEN TEXT
            )
        """.trimIndent()

        val createTableSpellTrap = """
            CREATE TABLE $TABLE_SPELL_TRAP (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT,
                $COL_CATEGORIA TEXT,
                $COL_TIPO TEXT,
                $COL_CODIGO TEXT,
                $COL_CANTIDAD INTEGER,
                $COL_IMAGEN TEXT
            )
        """.trimIndent()


    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableMonstruos)
        db?.execSQL(createTableSpellTrap)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MONSTRUOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SPELL_TRAP")
        onCreate(db)
    }

    fun insertImagePath(imagePath: String): Long {
        val values = ContentValues().apply {

            put( COL_IMAGEN, imagePath)
        }
        val db = writableDatabase
        val id = db.insert(TABLE_MONSTRUOS, null, values)
        db.close()
        return id
    }

    fun insertMonstruo(monstruo: Monstruo) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_CATEGORIA, "Monstruo")
            put(COL_CATEGORIA2, monstruo.categoria2)
            put(COL_NOMBRE, monstruo.nombre)
            put(COL_ATRIBUTO, monstruo.atributo)
            put(COL_NIVEL, monstruo.nivel)
            put(COL_TIPO, monstruo.tipo)
            put(COL_ATAQUE, monstruo.ataque)
            put(COL_DEFENSA, monstruo.defensa)
            put(COL_CODIGO, monstruo.codigo)
            put(COL_ESCALA, monstruo.escala)
            put(COL_CANTIDAD, monstruo.cantidad)
            put(COL_IMAGEN, monstruo.imagen)
        }
        db.insert(TABLE_MONSTRUOS, null, values)
        db.close()
    }

    fun insertSpellTrap(spellTrap: Spells_Traps) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOMBRE, spellTrap.nombre)
            put(COL_CATEGORIA, spellTrap.categoria)
            put(COL_TIPO, spellTrap.tipo)
            put(COL_CODIGO, spellTrap.codigo)
            put(COL_CANTIDAD, spellTrap.cantidad)
            put(COL_IMAGEN, spellTrap.imagen)
        }

        db.insert(TABLE_SPELL_TRAP, null, values)
        db.close()
    }

    fun updateMonstruo(monstruo: Monstruo) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_CATEGORIA, monstruo.categoria)
            put(COL_CATEGORIA2, monstruo.categoria2)
            put(COL_NOMBRE, monstruo.nombre)
            put(COL_ATRIBUTO, monstruo.atributo)
            put(COL_NIVEL, monstruo.nivel)
            put(COL_TIPO, monstruo.tipo)
            put(COL_ATAQUE, monstruo.ataque)
            put(COL_DEFENSA, monstruo.defensa)
            put(COL_CODIGO, monstruo.codigo)
            put(COL_ESCALA, monstruo.escala)
            put(COL_CANTIDAD, monstruo.cantidad)
            put(COL_IMAGEN, monstruo.imagen)
        }

        db.update(
            TABLE_MONSTRUOS,
            values,
            "$COL_ID = ?",
            arrayOf(monstruo.id.toString())
        )
        db.close()
    }

    fun updateSpellTrap(spellTrap: Spells_Traps) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOMBRE, spellTrap.nombre)
            put(COL_CATEGORIA, spellTrap.categoria)
            put(COL_TIPO, spellTrap.tipo)
            put(COL_CODIGO, spellTrap.codigo)
            put(COL_CANTIDAD, spellTrap.cantidad)
            put(COL_IMAGEN, spellTrap.imagen)
        }

        db.update(
            TABLE_SPELL_TRAP,
            values,
            "$COL_ID = ?",
            arrayOf(spellTrap.id.toString())
        )
        db.close()
    }

    fun deleteMonstruo(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_MONSTRUOS,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun deleteSpellTrap(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_SPELL_TRAP,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun getAllMonstruos(): List<Monstruo> {
        val monstruosList = mutableListOf<Monstruo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_MONSTRUOS WHERE categoria = 'Monstruo'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val categoria = cursor.getString(1) ?: "Monstruo"
                val categoria2 = cursor.getString(2) ?: ""
                val nombre = cursor.getString(3) ?: ""
                val atributo = cursor.getString(4)
                val nivel = cursor.getInt(5)
                val tipo = cursor.getString(6)
                val ataque = cursor.getInt(7)
                val defensa = cursor.getInt(8)
                val codigo = cursor.getString(9)
                val escala = cursor.getInt(10)
                val cantidad = cursor.getInt(11)
                val imagen = cursor.getString(12)

                val monstruo = Monstruo(id, categoria, categoria2, nombre, atributo, nivel, tipo,
                    ataque, defensa, codigo, escala, cantidad, imagen)

                monstruosList.add(monstruo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return monstruosList
    }


    fun getAllSpellTraps(): ArrayList<Spells_Traps> {
        val list = ArrayList<Spells_Traps>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_SPELL_TRAP", null)
        cursor?.let {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val categoria = cursor.getString(2)
                    val tipo = cursor.getString(3)
                    val codigo = cursor.getString(4)
                    val cantidad = cursor.getInt(5)
                    val imagen = cursor.getString(6)

                    list.add(Spells_Traps(id, nombre, categoria, tipo, codigo, cantidad, imagen))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        db.close()
        return list
    }


}