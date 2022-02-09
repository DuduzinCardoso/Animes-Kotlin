package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.Anime
import br.com.projetoindividual.dto.Personagem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    fun createAnime(@RequestBody anime: Anime): String {
        animes.add(
            Anime(
                nome = anime.nome,
                personagens = anime.personagens,
                criadores = anime.criadores
            )
        )

        return "Anime ${anime.nome} criado com sucesso!"
    }

    @DeleteMapping("/excluir/{nome}")
    fun deleteAnime(@PathVariable("nome") nome: String): String? {
        animes.removeIf { anime -> anime.nome == nome }
        return "Anime $nome excluído com sucesso!"
    }

    @PostMapping("/create/personagem/{anime}")
    fun createPersonagem(@RequestBody personagem: Personagem, @PathVariable("anime") animeNome: String): String {
        animes
            .filter { anime -> anime.nome == animeNome }

        return "Personagem ${personagem.nome} criado com sucesso!"
    }
}