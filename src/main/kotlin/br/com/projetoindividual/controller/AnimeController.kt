package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.*
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
    var animesDtos: ArrayList<AnimeDto>
) {
    @GetMapping("/get-animes")
    fun getListAnimes(): List<AnimeDto> {
        return animesDtos
    }

    @GetMapping("/info/anime/{nome}")
    fun getAnime(@PathVariable("nome") nome: String): AnimeDto? {
        return animesDtos.firstOrNull { anime -> anime.nome == nome }
    }

    @PostMapping("/anime/create")
    fun createAnime(@RequestBody anime: AnimeRequestDto): String {
        animesDtos.add(
            AnimeDto(
                id = UUID.randomUUID(),
                nome = anime.nome.trim(),
                personagens = ArrayList(),
                criadores = ArrayList()
            )
        )

        return "Anime ${anime.nome.trim()} criado com sucesso!"
    }

    @DeleteMapping("/excluir/{idAnime}")
    fun deleteAnime(@PathVariable("idAnime") nomeAnime: UUID): String? {
        animesDtos.removeIf { anime -> anime.id == nomeAnime }
        return "Anime excluído com sucesso!"
    }

    @DeleteMapping("/excluir/criador")
    fun deleteCriador(@RequestBody dadosCriador: DeleteCriadorRequestDto): String {

        val anime = animesDtos.firstOrNull { anime -> anime.id == dadosCriador.idAnime }
        var mensagemCriador = "Criador(a) excluído com sucesso!"

        if (anime!= null){
            val criador = anime.criadores.firstOrNull { criador -> criador.id == dadosCriador.idCriador }

            if (criador != null) {
                anime.criadores.removeIf { criador.id == dadosCriador.idCriador}
            }
            else{
                mensagemCriador = "Criador(a) não encontrado!"
            }
        } else{
            mensagemCriador = "Anime não encontrado!"
        }

        return mensagemCriador
    }

    @DeleteMapping("/excluir/personagem")
    fun deletePersonagem(@RequestBody dadosPersonagem: DeletePersonagemRequestDto): String {

        val anime = animesDtos.firstOrNull { anime -> anime.id == dadosPersonagem.idAnime }
        var mensagemPersonagem = "Personagem excluído com sucesso!"

        if (anime!= null){
            val personagem = anime.personagens.firstOrNull { personagem-> personagem.id == dadosPersonagem.idPersonagem }

            if (personagem != null) {
                anime.personagens.removeIf { personagem.id == dadosPersonagem.idPersonagem}
            }
            else{
                mensagemPersonagem = "Personagem não encontrado!"
            }
        } else{
            mensagemPersonagem = "Anime não encontrado!"
        }

        return mensagemPersonagem
    }

    @PostMapping("/create/personagem/{idAnime}")
    fun createPersonagem(@RequestBody personagem: PersonagemRequestDto, @PathVariable("idAnime")
    idAnime: UUID): String {
        val animeF = animesDtos.firstOrNull { anime -> anime.id == idAnime }

        if (animeF != null) {
            animeF.personagens.add(
                PersonagemDto(
                    id = UUID.randomUUID(),
                    nome = personagem.nome,
                    genero = personagem.genero,
                )
            )
        } else {
            return "Anime não encontrado!"
        }

        return "Personagem ${personagem.nome} criado com sucesso!"
    }
    @PostMapping("/create/criador/{idAnime}")
    fun createCriador(@RequestBody criador: CriadorRequestDto, @PathVariable("idAnime") idAnime: UUID): String{
        val animeCreate = animesDtos.firstOrNull { anime -> anime.id == idAnime}

        if (animeCreate == null) {
            return "Anime não encontrado!"
        } else {
            animeCreate.criadores.add(
                CriadorDto(
                    id = UUID.randomUUID(),
                    nome = criador.nome,
                    nascimento = criador.nascimento,
                )
            )
        }
        return "Criador ${criador.nome} criado com sucesso!"
    }
    @PatchMapping("/update/personagem")
    fun updatePersonagem(@RequestBody dadosPersonagem: UpdatePersonagemRequestDto): String {
        var mensagemPersonagem = "Personagem atualizado com sucesso"
        val anime = animesDtos.firstOrNull { anime -> anime.id == dadosPersonagem.idAnime }

        if (anime != null) {
            val personagem =anime.personagens.firstOrNull { personagem -> personagem.id == dadosPersonagem.idPersonagem }

            if (personagem != null) {
                personagem.nome = dadosPersonagem.nome
            }
            else {
                mensagemPersonagem = "Personagem não encontrado!"
            }
        } else {
            mensagemPersonagem = "Anime não encontrado!"
        }

        return mensagemPersonagem

        }
    @PatchMapping("/update/criador")
    fun updateCriador(@RequestBody dadosCriador: UpdateCriadorRequestDto): String {
        var mensagemCriador = "Criador(a) atualizado com sucesso"
        val anime = animesDtos.firstOrNull { anime -> anime.id == dadosCriador.idAnime}

        if (anime != null) {
            val criador = anime.criadores.firstOrNull { anime -> anime.id == dadosCriador.idCriador }

            if (criador != null) {
                criador.nome = dadosCriador.nome
            }
            else {
                mensagemCriador = "Criador(a) não encontrado!"
            }
        } else {
            mensagemCriador = "Anime não encontrado!"
        }

        return mensagemCriador
    }
}