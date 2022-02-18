package br.com.projetoindividual.dto

import java.util.*

data class DeletePersonagemRequestDto(
    val idAnime: UUID,
    val idPersonagem: UUID,
)
