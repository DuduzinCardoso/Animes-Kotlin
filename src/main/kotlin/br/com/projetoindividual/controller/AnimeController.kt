package br.com.projetoindividual.controller

import br.com.projetoindividual.domain.Anime
import br.com.projetoindividual.domain.Criador
import br.com.projetoindividual.domain.Personagem
import br.com.projetoindividual.dto.*
import br.com.projetoindividual.repository.AnimeRepository
import br.com.projetoindividual.repository.CriadorRepository
import br.com.projetoindividual.repository.PersonagemRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/animes")
class AnimeController(
    var animesDtos: ArrayList<AnimeDto>,
    val animeRepository: AnimeRepository,
    val personagemRepository: PersonagemRepository,
    val criadorRepository: CriadorRepository,
) {
    @GetMapping("/get-animes")
    fun getListAnimes(): List<AnimeDto> {
        return animeRepository.findAll()
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
                        } as ArrayList<PersonagemDto>,
                    criadores = criadorRepository.findByIdAnime(anime.id)
                        .map { criador ->
                            CriadorDto(
                                id = criador.id,
                                nome = criador.nome,
                                nascimento = criador.nascimento
                            )
                        } as ArrayList<CriadorDto>
                )
            }
    }

    @PostMapping("/anime/create")
    fun createAnime(@RequestBody anime: AnimeRequestDto): String {
        animeRepository.save(Anime(nome = anime.nome.trim()))

        return "Anime ${anime.nome.trim()} criado com sucesso!"
    }

    @GetMapping("/info/anime/{nome}")
    fun getAnime(@PathVariable("nome") nome: String): AnimeDto? {
        return animesDtos.firstOrNull { anime -> anime.nome == nome }
    }

    @DeleteMapping("/deletar/anime/{idAnime}")
    fun deleteAnime(@PathVariable("idAnime") idAnime: Long): String? {
        var mensagem = "Anime deletado com sucesso!"
        try {
            animeRepository.deleteById(idAnime)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar anime, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/deletar/criador/{idCriador}")
    fun deleteCriador(@PathVariable idCriador: Long): String {
        var mensagem = "Criador deletado com sucesso!"
        try {
            criadorRepository.deleteById(idCriador)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar criador, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/deletar/personagem/{idPersonagem}")
    fun deletePersonagem(@PathVariable idPersonagem: Long): String {
        var mensagem = "Personagem deletado com sucesso!"
        try {
            personagemRepository.deleteById(idPersonagem)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar personagem, por favor, tente novamente!"
        }

        return mensagem
    }

    @PostMapping("/create/personagem/{idAnime}")
    fun createPersonagem(
        @RequestBody personagem: PersonagemRequestDto, @PathVariable("idAnime")
        idAnime: Long
    ): String {
        var anime = animeRepository.getById(idAnime)

        if (anime != null) {
            personagemRepository.save(
                Personagem(
                    anime = anime,
                    nome = personagem.nome,
                    genero = personagem.genero
                )
            )
        }

        return "Personagem ${personagem.nome} criado com sucesso!"
    }

    @PostMapping("/create/criador/{idAnime}")
    fun createCriador(@RequestBody criador: CriadorRequestDto, @PathVariable("idAnime") idAnime: Long): String {
        var anime = animeRepository.getById(idAnime)

        if (anime != null) {
            criadorRepository.save(
                Criador(
                    anime = anime,
                    nome = criador.nome,
                    nascimento = criador.nascimento
                )
            )
        }

        return "Criador ${criador.nome} criado com sucesso!"
    }

    @PatchMapping("/update/personagem")
    fun updatePersonagem(@RequestBody dadosPersonagem: UpdatePersonagemRequestDto): String {
        return ""
    }

    @PatchMapping("/update/criador")
    fun updateCriador(@RequestBody dadosCriador: UpdateCriadorRequestDto): String {
        return ""
    }
}