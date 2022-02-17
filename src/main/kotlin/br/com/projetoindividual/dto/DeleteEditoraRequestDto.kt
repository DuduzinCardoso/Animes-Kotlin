package br.com.projetoindividual.dto

import java.util.*

data class DeleteEditoraRequestDto(
    val idManga: UUID,
    val idEditora: UUID,
)
