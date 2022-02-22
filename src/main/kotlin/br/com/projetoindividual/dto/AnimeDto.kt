package br.com.projetoindividual.dto

import java.util.UUID

data class AnimeDto(
    var id: Long? = null,
    var nome: String,
    var personagens: List<PersonagemDto> ? = null,
    var criadores: List<CriadorDto>? = null,
)
