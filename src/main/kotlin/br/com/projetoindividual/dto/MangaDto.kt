package br.com.projetoindividual.dto

import java.util.*
import kotlin.collections.ArrayList

data class MangaDto(
    var id: Long? = null,
    var nome: String,
    var editoras: List<EditoraDto>? = null,
    var escritores: List<EscritorDto>? = null,
)
