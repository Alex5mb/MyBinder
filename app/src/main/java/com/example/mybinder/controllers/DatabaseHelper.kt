package com.example.mybinder.controllers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mybinder.Model.Carta
import com.example.mybinder.Model.Mazo
import com.example.mybinder.Model.Monstruo
import com.example.mybinder.Model.Spells_Traps
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ATAQUE
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ATRIBUTO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CAMBIO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CANTIDAD
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CATEGORIA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CATEGORIA2
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_CODIGO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_DEFENSA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ESCALA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_ID
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_IDMAZO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_IMAGEN
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_NIVEL
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_NOMBRE
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.COL_TIPO
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.DATABASE_NAME
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.TABLE_INTERMEDIA
import com.example.mybinder.controllers.MonstruoContract.Companion.Entrada.Companion.TABLE_MAZO
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
                $COL_IMAGEN TEXT,
                $COL_CAMBIO INT
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
                $COL_IMAGEN TEXT,
                $COL_CAMBIO INT
            )
        """.trimIndent()

        val createTableMazo = """
            CREATE TABLE $TABLE_MAZO (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT
            )
        """.trimIndent()

        val createTableIntermedia = """
             CREATE TABLE $TABLE_INTERMEDIA(
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_IMAGEN TEXT,
                $COL_IDMAZO INTEGER
            )
        """.trimIndent()

        val dropTableMazo = "DROP TABLE IF EXISTS $TABLE_MAZO"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        //db?.execSQL(dropTableMazo)
        db?.execSQL(createTableMazo)
        db?.execSQL(createTableIntermedia)

        db?.execSQL(createTableMonstruos)
        db?.execSQL(createTableSpellTrap)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MONSTRUOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SPELL_TRAP")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MAZO")
        onCreate(db)
    }


    @SuppressLint("Range")
    fun obtenerImagenesIntermedia(database: SQLiteDatabase, mazoId: Int): ArrayList<Carta> {
        val imagenes = ArrayList<Carta>()
        val query = "SELECT * FROM $TABLE_INTERMEDIA WHERE $COL_IDMAZO = ?"
        val cursor = database.rawQuery(query, arrayOf(mazoId.toString()))
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            val imagen = cursor.getString(cursor.getColumnIndex(COL_IMAGEN))
            imagenes.add(Carta(id,imagen,mazoId))
        }
        cursor.close()
        return imagenes
    }

    fun insertMazo(mazo: Mazo){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_NOMBRE, mazo.nombre)
        }
        db.insert(TABLE_MAZO,null,values)
        db.close()

    }
    fun obtenerUltimoIdDeTablaMazo(contexto: Context): Int {
        val dbHelper = DatabaseHelper(contexto)
        val db = dbHelper.readableDatabase

        val query = "SELECT MAX($COL_ID) FROM $TABLE_MAZO"
        val cursor = db.rawQuery(query, null)

        val ultimoId = if (cursor.moveToFirst()) cursor.getInt(0) else 0

        cursor.close()
        db.close()

        return ultimoId
    }



    fun insertCard_Mazo(id: Int, carta: Carta){
        val db = writableDatabase

        val values = ContentValues().apply {
          put(COL_IMAGEN, carta.imagen)
          put(COL_IDMAZO, id)
        }
        db.insert(TABLE_INTERMEDIA,null,values)
        db.close()
    }

    fun deleteAllMazos() {
        val db = writableDatabase
        db.delete(TABLE_MAZO, null, null)
        db.close()
    }

    fun insertMonstruo(monstruo: Monstruo) {
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
            put(COL_CAMBIO, monstruo.cambio)
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
            put(COL_CAMBIO, spellTrap.cambio)
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
            put(COL_CAMBIO, monstruo.cambio)
        }

        db.update(TABLE_MONSTRUOS, values, "$COL_ID = ?", arrayOf(monstruo.id.toString())
        )
        db.close()
    }

    fun updateMazo(mazo:Mazo){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COL_NOMBRE, mazo.nombre)
        }
        db.update(TABLE_MAZO,values,"$COL_ID = ?", arrayOf(mazo.id.toString()))
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
            put(COL_CAMBIO, spellTrap.cambio)
        }

        db.update(TABLE_SPELL_TRAP, values, "$COL_ID = ?", arrayOf(spellTrap.id.toString()))
        db.close()
    }

    fun deleteAllCardDeck(id:Int){
        val db = writableDatabase
        db.delete(
            TABLE_INTERMEDIA,
            "$COL_IDMAZO = ?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun deleteDeck(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_MAZO,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
    }
    fun deleteTablaIntermedia(id: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_INTERMEDIA,
            "$COL_ID = ?",
            arrayOf(id.toString())
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
        val query = "SELECT * FROM $TABLE_MONSTRUOS "
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
                val cambio = cursor.getInt(13) == 1

                val monstruo = Monstruo(id, categoria, categoria2, nombre, atributo, nivel, tipo,
                    ataque, defensa, codigo, escala, cantidad, imagen, cambio)

                monstruosList.add(monstruo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return monstruosList
    }

    fun gerAllDecks(): ArrayList<Mazo>{
        val list = ArrayList<Mazo>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_MAZO", null)
        cursor?.let {
            if (cursor.moveToFirst()) {
                do {
                    val id = it.getInt(0)
                    val nombre = it.getString(1)
                    val mazo = Mazo(id, nombre)
                    list.add(mazo)

                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        db.close()
        return list
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
                    val cambio = cursor.getInt(7) == 1

                    list.add(Spells_Traps(id, nombre, categoria, tipo, codigo, cantidad, imagen, cambio))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        db.close()
        return list
    }


    @SuppressLint("Range")
    fun buscarMonstruos(nombre: String?, categoria: String?, tipo: String?, codigo: String?,
                        categoria2: String?, minNivel: Int?, maxNivel: Int?, atributo: String?,
                        minEscala: Int?, maxEscala: Int?, minAtaque: Int?, maxAtaque: Int?,
                        minDefensa: Int?, maxDefensa: Int?): List<Monstruo> {

        val db =readableDatabase

        val query = "SELECT * FROM $TABLE_MONSTRUOS " +
                "WHERE ${if(nombre != "" ) "nombre LIKE '%$nombre%' AND " else ""}" +
                "${if(categoria !="") "categoria LIKE '%$categoria%' AND " else ""}" +
                "${if(tipo !="") "tipo LIKE '%$tipo%'AND " else ""}"+
                "${if(codigo != "") "codigo = '$codigo' AND " else ""}"+
                "${if(categoria2 != "") "categoria2  LIKE '%$categoria2%' AND " else ""}"+
                "nivel BETWEEN ${minNivel ?: 0} AND ${maxNivel ?: 12} " +
                "AND ${if(atributo != "") "atributo LIKE '$atributo' AND " else ""}"+
                "escala BETWEEN ${minEscala ?: 0} AND ${maxEscala ?: 13} " +
                "AND ataque BETWEEN ${minAtaque ?: 0} AND ${maxAtaque ?: 9999} " +
                "AND defensa BETWEEN ${minDefensa ?: 0} AND ${maxDefensa ?: 9999}"

                    val cursor = db.rawQuery(query, null)
        val monstruos = mutableListOf<Monstruo>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val categoria = cursor.getString(1) ?: ""
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
            val cambio = cursor.getInt(13) == 1

            val monstruo = Monstruo(id, categoria, categoria2, nombre, atributo, nivel, tipo,
                ataque, defensa, codigo, escala, cantidad, imagen, cambio)
            monstruos.add(monstruo)
        }
        cursor.close()
        db.close()

        return monstruos
    }

    @SuppressLint("Range")
    fun buscarSpellTraps(nombre: String?, categoria: String?, tipo: String?, codigo: String?): List<Spells_Traps> {

        val spellTraps = mutableListOf<Spells_Traps>()

        val db = this.readableDatabase

        val selectQuery = "SELECT * FROM $TABLE_SPELL_TRAP " +
                "WHERE ${if(nombre != "" ) "nombre LIKE '%$nombre%' AND " else ""}" +
                "categoria LIKE '%$categoria%'" +
                "AND tipo LIKE '%$tipo%'"+
                "${if(codigo != "") " AND codigo = '$codigo'" else ""}"

        val cursor = db.rawQuery(selectQuery, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val categoria = cursor.getString(2)
            val tipo = cursor.getString(3)
            val codigo = cursor.getString(4)
            val cantidad = cursor.getInt(5)
            val imagen = cursor.getString(6)
            val cambio = cursor.getInt(7) == 1

            val spellTrap = Spells_Traps(id, nombre, categoria, tipo, codigo, cantidad, imagen, cambio)
            spellTraps.add(spellTrap)
        }

        cursor.close()
        db.close()

        return spellTraps
    }


    @SuppressLint("Range")
    fun getMonstruosConCambio(): List<Monstruo> {
        val monstruos = mutableListOf<Monstruo>()
        val query = "SELECT * FROM $TABLE_MONSTRUOS WHERE $COL_CAMBIO = 1"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(COL_ID))
                val categoria = getString(getColumnIndex(COL_CATEGORIA))
                val categoria2 = getString(getColumnIndex(COL_CATEGORIA2))
                val nombre = getString(getColumnIndex(COL_NOMBRE))
                val atributo = getString(getColumnIndex(COL_ATRIBUTO))
                val nivel = getInt(getColumnIndex(COL_NIVEL))
                val tipo = getString(getColumnIndex(COL_TIPO))
                val ataque = getInt(getColumnIndex(COL_ATAQUE))
                val defensa = getInt(getColumnIndex(COL_DEFENSA))
                val codigo = getString(getColumnIndex(COL_CODIGO))
                val escala = getInt(getColumnIndex(COL_ESCALA))
                val cantidad = getInt(getColumnIndex(COL_CANTIDAD))
                val imagen = getString(getColumnIndex(COL_IMAGEN))
                val cambio = getInt(getColumnIndex(COL_CAMBIO)) == 1

                val monstruo = Monstruo(id, categoria, categoria2, nombre, atributo, nivel, tipo, ataque, defensa, codigo, escala, cantidad, imagen, cambio)
                monstruos.add(monstruo)
            }
        }
        cursor.close()
        db.close()
        return monstruos
    }

    fun getSpellTrapsWithChanges(): List<Spells_Traps> {
        val spellTraps = mutableListOf<Spells_Traps>()
        val query = "SELECT * FROM $TABLE_SPELL_TRAP WHERE $COL_CAMBIO = 1"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val spellTrap = Spells_Traps(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getString(6),
                cursor.getInt(7) == 1
            )
            spellTraps.add(spellTrap)
        }

        cursor.close()
        db.close()

        return spellTraps
    }

    fun getAllMainMonstruos(): List<Monstruo> {
        val monstruosList = mutableListOf<Monstruo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_MONSTRUOS WHERE $COL_CATEGORIA2 IN ('Normal', 'Efecto', 'Ritual', 'Pendulo')"
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
                val cambio = cursor.getInt(13) == 1

                val monstruo = Monstruo(id, categoria, categoria2, nombre, atributo, nivel, tipo,
                    ataque, defensa, codigo, escala, cantidad, imagen, cambio)

                monstruosList.add(monstruo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return monstruosList
    }


    @SuppressLint("Range")
    fun buscarMonstruosNombre(nombre: String?): List<Monstruo> {

        val db =readableDatabase

        val query = "SELECT * FROM $TABLE_MONSTRUOS " +
                "WHERE ${if (nombre != "") "nombre LIKE '%$nombre%' AND " else ""}" +
                "categoria2 IN ('Normal', 'Efecto')"

        val cursor = db.rawQuery(query, null)
        val monstruos = mutableListOf<Monstruo>()
        while (cursor.moveToNext()) {
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
            val cambio = cursor.getInt(13) == 1

            val monstruo = Monstruo(id, categoria, categoria2, nombre, atributo, nivel, tipo,
                ataque, defensa, codigo, escala, cantidad, imagen, cambio)
            monstruos.add(monstruo)
        }
        cursor.close()
        db.close()

        return monstruos
    }

    @SuppressLint("Range")
    fun buscarSpellTrapsNombre(nombre: String?): List<Spells_Traps> {

        val spellTraps = mutableListOf<Spells_Traps>()

        val db = this.readableDatabase

        val selectQuery = "SELECT * FROM $TABLE_SPELL_TRAP " +
                "WHERE ${if(nombre != "") "nombre LIKE '%$nombre%'" else "1=1"}"


        val cursor = db.rawQuery(selectQuery, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val categoria = cursor.getString(2)
            val tipo = cursor.getString(3)
            val codigo = cursor.getString(4)
            val cantidad = cursor.getInt(5)
            val imagen = cursor.getString(6)
            val cambio = cursor.getInt(7) == 1

            val spellTrap = Spells_Traps(id, nombre, categoria, tipo, codigo, cantidad, imagen, cambio)
            spellTraps.add(spellTrap)
        }

        cursor.close()
        db.close()

        return spellTraps
    }







}