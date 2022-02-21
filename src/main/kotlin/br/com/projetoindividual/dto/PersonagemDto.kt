package br.com.projetoindividual.dto

import java.util.*

data class PersonagemDto(
    var id: Long? = null,
    var nome: String? = null,
    val genero: String? = null,
)
