package br.com.projetoindividual.controller

import br.com.projetoindividual.domain.Anime
import br.com.projetoindividual.domain.Criador
import br.com.projetoindividual.domain.Personagem
import br.com.projetoindividual.dto.*
import br.com.projetoindividual.repository.AnimeRepository
import br.com.projetoindividual.repository.CriadorRepository
import br.com.projetoindividual.repository.PersonagemRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/anime")
class AnimeController(
    val animeRepository: AnimeRepository,
    val personagemRepository: PersonagemRepository,
    val criadorRepository: CriadorRepository,
) {
    @GetMapping("/animes")
    fun getListAnimes(): ResponseEntity<List<AnimeDto>> {
        try {
            return ResponseEntity.ok(animeRepository.findAll()
                .map { anime ->
                    AnimeDto(
                        id = anime.id,
                        nome = anime.nome,
                        personagens = personagemRepository.findByIdAnime(anime.id)
                            .map { personagem ->
                                PersonagemDto(
                                    id = personagem.id,
                                    nome = personagem.nome,
                                    genero = personagem.genero
                                )
                            },
                        criadores = criadorRepository.findByIdAnime(anime.id)
                            .map { criador ->
                                CriadorDto(
                                    id = criador.id,
                                    nome = criador.nome,
                                    nascimento = criador.nascimento
                                )
                            }
                    )
                })
        } catch (exception: Exception) {
            return ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/create")
    fun createAnime(@RequestBody anime: AnimeRequestDto): String {
        var mensagem = "Anime ${anime.nome.trim()} criado com sucesso!"

        try {
            animeRepository.save(Anime(nome = anime.nome.trim()))
        } catch (exception: Exception) {
            mensagem = "Falha ao cadastrar anime!"
        }

        return mensagem
    }

    @GetMapping("/{id}")
    fun getAnime(@PathVariable("id") id: Long): ResponseEntity<AnimeDto> {
        try {
            val anime = animeRepository.getById(id)

            if (anime != null) {
                return ResponseEntity.ok(
                    AnimeDto(
                        id = anime.id,
                        nome = anime.nome,
                        personagens = personagemRepository.findByIdAnime(anime.id)
                            .map { personagem ->
                                PersonagemDto(
                                    id = personagem.id,
                                    nome = personagem.nome,
                                    genero = personagem.genero
                                )
                            },
                        criadores = criadorRepository.findByIdAnime(anime.id)
                            .map { criador ->
                                CriadorDto(
                                    id = criador.id,
                                    nome = criador.nome,
                                    nascimento = criador.nascimento
                                )
                            }
                    )
                )
            }
        } catch (exception: Exception) {
            println(exception.message)
        }

        return ResponseEntity.notFound().build()
    }

    @PostMapping("/personagem")
    fun createPersonagem(
        @RequestBody personagem: PersonagemRequestDto
    ): String {
        var mensagem = "Anime não encontrado"

        try {
            val anime = animeRepository.getById(personagem.idAnime)

            if (anime != null) {
                personagemRepository.save(
                    Personagem(
                        anime = anime,
                        nome = personagem.nome,
                        genero = personagem.genero
                    )
                )

                mensagem = "Personagem ${personagem.nome} criado com sucesso!"
            }
        } catch (exception: Exception) {
            println(exception.message)
        }

        return mensagem
    }

    @PostMapping("/criador")
    fun createCriador(@RequestBody criador: CriadorRequestDto): String {
        var mensagem = "Criador ${criador.nome} criado com sucesso!"

        try {
            val anime = animeRepository.getById(criador.idAnime)

            if (anime != null) {
                criadorRepository.save(
                    Criador(
                        anime = anime,
                        nome = criador.nome,
                        nascimento = criador.nascimento
                    )
                )
            }
        } catch (exception: Exception) {
            mensagem = "Falha ao cadastrar criador!"
        }

        return mensagem
    }

    @PutMapping("/personagem")
    fun updatePersonagem(@RequestBody dadosPersonagem: UpdatePersonagemRequestDto): String {
        var mensagem = "Personagem atualizado com sucesso!"

        try {
            personagemRepository.update(dadosPersonagem.id, dadosPersonagem.nome, dadosPersonagem.genero)

        } catch (exception: Exception) {
            mensagem = "Falha ao atualizar personagem!"
            println(exception)
        }

        return mensagem
    }

    @PutMapping("/criador")
    fun updateCriador(@RequestBody dadosCriador: UpdateCriadorRequestDto): String {
       var mensagem = "Criador atualizado com sucesso!"

        try {
            criadorRepository.update(dadosCriador.id, dadosCriador.nome, dadosCriador.nascimento)
        } catch (exception: Exception){
            mensagem = "Falha ao atualizar criador!"
            println(exception)
        }

        return mensagem
    }

    @DeleteMapping("/{idAnime}")
    fun deleteAnime(@PathVariable("idAnime") idAnime: Long): String? {
        var mensagem = "Anime deletado com sucesso!"

        try {
            animeRepository.deleteById(idAnime)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar anime, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/criador/{idCriador}")
    fun deleteCriador(@PathVariable idCriador: Long): String {
        var mensagem = "Criador deletado com sucesso!"

        try {
            criadorRepository.deleteById(idCriador)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar criador, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/personagem/{idPersonagem}")
    fun deletePersonagem(@PathVariable idPersonagem: Long): String {
        var mensagem = "Personagem deletado com sucesso!"

        try {
            personagemRepository.deleteById(idPersonagem)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar personagem, por favor, tente novamente!"
        }

        return mensagem
    }
}