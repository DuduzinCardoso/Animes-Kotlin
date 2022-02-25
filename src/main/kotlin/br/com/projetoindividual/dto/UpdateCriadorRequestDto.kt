package br.com.projetoindividual.dto

import java.util.*

data class UpdateCriadorRequestDto(
    val id: Long,
    val nome: String,
    val nascimento: Date,
) {

}
