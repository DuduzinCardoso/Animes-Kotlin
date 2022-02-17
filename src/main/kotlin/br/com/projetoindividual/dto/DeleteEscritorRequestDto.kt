package br.com.projetoindividual.dto

import java.util.*

data class DeleteEscritorRequestDto(
    val idManga: UUID,
    val idEscritor: UUID,
)
