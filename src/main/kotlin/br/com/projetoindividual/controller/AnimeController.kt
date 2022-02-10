package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.Anime
import br.com.projetoindividual.dto.AnimeRequestDto
import br.com.projetoindividual.dto.Criador
import br.com.projetoindividual.dto.Personagem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/animes")
class AnimeController(
    var animes: ArrayList<Anime>
) {
    @GetMapping("/get-animes")
    fun getListAnimes(): List<Anime> {
        return animes
    }

    @GetMapping("/info/{nome}")
    fun getAnime(@PathVariable("nome") nome: String): Anime? {
        return animes.firstOrNull { anime -> anime.nome == nome }
    }

    @PostMapping("/create")
    fun createAnime(@RequestBody anime: AnimeRequestDto): String {
        animes.add(
            Anime(
                nome = anime.nome.trim(),
                personagens = ArrayList(),
                criadores = ArrayList()
            )
        )

        return "Anime ${anime.nome.trim()} criado com sucesso!"
    }

    @DeleteMapping("/excluir/{nome}")
    fun deleteAnime(@PathVariable("nome") nome: String): String? {
        animes.removeIf { anime -> anime.nome == nome }
        return "Anime $nome excluído com sucesso!"
    }

    @PostMapping("/create/personagem/{anime}")
    fun createPersonagem(@RequestBody personagem: Personagem, @PathVariable("anime") animeNome: String): String {
        val animeF = animes.firstOrNull { anime -> anime.nome == animeNome.trim() }

        if (animeF != null) {
            animeF.personagens.add(personagem)
        } else {
            return "Anime $animeNome não encontrado!"
        }

        return "Personagem ${personagem.nome} criado com sucesso!"
    }
    @PostMapping("/create/criador/{anime}")
    fun createCriador(@RequestBody criador: Criador, @PathVariable("anime") animeNome: String): String{
        val animeCreate = animes.firstOrNull { anime -> anime.nome == animeNome.trim()}

        if (animeCreate == null){
            return "Anime $animeNome não encontrado!"
        } else{
            animeCreate.criadores.add(criador)
        }
        return "Criador ${criador.nome} criado com sucesso!"
    }
    @PatchMapping("/update/{animeNome}/personagem/{nomePersonagem}")
    fun updatePersonagem(
        @PathVariable("nomePersonagem") nomePersonagem: String,
        @PathVariable("animeNome") animeNome: String
    ): String {
        val nomeDoAnime = animes.firstOrNull { anime -> anime.nome == animeNome.trim() }
        val nomeDoPersonagem = animes.firstOrNull { personagem -> personagem.nome == nomePersonagem.trim() }

        if (nomeDoAnime != null){
            if (nomeDoPersonagem != null){
                nomeDoPersonagem.nome = nomePersonagem
            }
            else{
                return "Personagem $nomePersonagem não encontrado!"
            }
        }
        else{
            return "Anime $animeNome não encontrado!"
        }
        return "Personagem $nomePersonagem atualizado com sucesso"
    }
}