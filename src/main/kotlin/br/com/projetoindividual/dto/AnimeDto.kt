package br.com.projetoindividual.dto

import java.util.UUID

data class AnimeDto(
    var id: UUID,
    var nome: String,
    var personagens: ArrayList<PersonagemDto>,
    var criadores: ArrayList<CriadorDto>,
)
