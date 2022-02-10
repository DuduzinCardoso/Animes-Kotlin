package br.com.projetoindividual.dto

data class Anime(
    var nome: String,
    var personagens: ArrayList<Personagem>,
    var criadores: ArrayList<Criador>,
)
