package br.com.projetoindividual.dto

import java.util.*

data class UpdatePersonagemRequestDto(
    val idAnime: UUID,
    val idPersonagem: UUID,
    val nome: String,
)
