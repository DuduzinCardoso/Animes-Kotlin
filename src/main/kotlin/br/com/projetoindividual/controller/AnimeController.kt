package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.Anime
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
        animes.add(Anime(
            nome = anime.nome,
            personagens = anime.personagens
        ))

        return "Anime ${anime.nome} criado com sucesso!"
    }
}