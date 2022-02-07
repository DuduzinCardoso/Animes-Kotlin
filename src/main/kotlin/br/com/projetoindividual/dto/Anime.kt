package br.com.projetoindividual.dto

data class Anime(
    val nome: String,
    var personagens: ArrayList<Personagem>? = null
)
