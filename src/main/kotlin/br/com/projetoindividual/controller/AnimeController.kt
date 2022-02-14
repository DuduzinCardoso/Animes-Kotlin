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
    var animeDtos: ArrayList<AnimeDto>
) {
    @GetMapping("/get-animes")
    fun getListAnimes(): List<AnimeDto> {
        return animeDtos
    }

    @GetMapping("/info/{nome}")
    fun getAnime(@PathVariable("nome") nome: String): AnimeDto? {
        return animeDtos.firstOrNull { anime -> anime.nome == nome }
    }

    @PostMapping("/create")
    fun createAnime(@RequestBody anime: AnimeRequestDto): String {
        animeDtos.add(
            AnimeDto(
                id = UUID.randomUUID(),
                nome = anime.nome.trim(),
                personagens = ArrayList(),
                criadores = ArrayList()
            )
        )

        return "Anime ${anime.nome.trim()} criado com sucesso!"
    }

    @DeleteMapping("/excluir/{idNome}")
    fun deleteAnime(@PathVariable("idNome") nome: UUID): String? {
        animeDtos.removeIf { anime -> anime.id == nome }
        return "Anime excluído com sucesso!"
    }

    @DeleteMapping("/excluir/{idAnime}/{idCriador}")
    fun deleteCriador(
        @PathVariable("idCriador") nomeOne: UUID,
        @PathVariable("idAnime") nomeTwo: UUID
    ): String {
        animeDtos.removeIf { anime -> anime.id == nomeTwo }
        animeDtos.removeIf { criador -> criador.id == nomeOne }
        return "Criador excluído com sucesso!"
    }


    @PostMapping("/create/personagem/{idAnime}")
    fun createPersonagem(@RequestBody personagem: PersonagemRequestDto, @PathVariable("idAnime") idAnime: UUID): String {
        val animeF = animeDtos.firstOrNull { anime -> anime.id == idAnime }

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
        val animeCreate = animeDtos.firstOrNull { anime -> anime.id == idAnime}

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
    @PatchMapping("/update/{idAnime}/{novoNomeCriador}/{idCriador}")
    fun updateCriador(
        @PathVariable ("novoNomeCriador")novoNomeDoCriador: String,
        @PathVariable("idCriador") idCriador:UUID,
        @PathVariable("idAnime") idAnime: UUID
    ): String {
        val nomeDoAnime = animeDtos.firstOrNull { anime -> anime.id == idAnime }
        val nomeDoCriador = nomeDoAnime!!.criadores.firstOrNull { criador -> criador.id == idCriador}

            if (nomeDoCriador != null){
                nomeDoCriador.nome = novoNomeDoCriador
            }
            else{
                return "Criador não encontrado!"
            }
        return "Criador atualizado com sucesso"
    }
}