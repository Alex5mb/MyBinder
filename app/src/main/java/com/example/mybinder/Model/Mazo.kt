package com.example.mybinder.Model

class Mazo(id: Int, nombre: String, mainDeck: List<Any>, extraDeck: List<Monstruo>, sideDeck: List<Any>) {

    val id: Int = 0
    val nombre: String = ""
    val mainDeck: MutableList<Any> = mutableListOf()
    val extraDeck: MutableList<Monstruo> = mutableListOf()
    val sideDeck: MutableList<Any> = mutableListOf()

    fun addCartaMainMonstruo(monstruo: Monstruo){
        if (mainDeck.size < 60) mainDeck.add(monstruo)
    }
    fun addCartaMainSpellTrap(spelltrap: Spells_Traps){
        if (mainDeck.size < 60) mainDeck.add(spelltrap)
    }

    fun addCartaExtra(monstruo: Monstruo){
        if(extraDeck.size < 15 && (monstruo.categoria2 != "Normal" || monstruo.categoria2 != "Efecto" || monstruo.categoria2 != "Ritual" || monstruo.categoria2 != "Pendulo")){
            extraDeck.add(monstruo)
        }
    }

    fun addCartaSideMonstruo(monstruo: Monstruo){
        if (sideDeck.size < 15) sideDeck.add(monstruo)
    }
    fun addCartaSideSpellTrap(spelltrap: Spells_Traps){
        if (sideDeck.size < 15) sideDeck.add(spelltrap)
    }


}