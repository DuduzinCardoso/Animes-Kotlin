package br.com.projetoindividual.dto

import java.util.*
import kotlin.collections.ArrayList

data class MangaDto(
    var id: UUID,
    var nome: String,
    var personagens: ArrayList<PersonagemDto>,
    var escritores: ArrayList<EscritorDto>,
)
