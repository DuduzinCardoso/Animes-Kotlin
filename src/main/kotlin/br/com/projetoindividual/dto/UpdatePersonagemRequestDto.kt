package br.com.projetoindividual.dto

data class UpdatePersonagemRequestDto(
    val id: Long,
    val nome: String,
    val genero: String,
)
