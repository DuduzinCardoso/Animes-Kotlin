package br.com.projetoindividual.dto

import java.util.*

data class DeleteCriadorRequestDto(
    val idAnime: UUID,
    val idCriador: UUID,
)
