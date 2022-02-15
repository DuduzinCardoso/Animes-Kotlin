package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.EscritorDto
import br.com.projetoindividual.dto.EscritorRequestDto
import br.com.projetoindividual.dto.MangaDto
import br.com.projetoindividual.dto.MangaRequestDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/mangas")
class MangaController (
    var mangasDtos: ArrayList<MangaDto>
    ){
    @GetMapping("/get-mangas")
    fun getListManga(): List<MangaDto>{
        return mangasDtos
    }

    @GetMapping("/info/manga/{nome}")
    fun getAnime(@PathVariable("nome") nome: String): MangaDto? {
        return mangasDtos.firstOrNull { manga -> manga.nome == nome }
    }
    @PostMapping("/manga/create")
    fun createManga(@RequestBody manga: MangaRequestDto): String {
        mangasDtos.add(
            MangaDto(
                id = UUID.randomUUID(),
                nome = manga.nome.trim(),
                personagens = ArrayList(),
                escritores = ArrayList()
            )
        )
        return "Manga ${manga.nome.trim()} criado com sucesso!"
    }
    @DeleteMapping("/excluir/{idManga}")
    fun deleteManga(@PathVariable("idManga") nomeManga: UUID): String?{
        mangasDtos.removeIf { manga -> manga.id ==nomeManga }
        return "Manga excluído com sucesso!"
    }
    @PostMapping("/create/escritor/{idManga}")
    fun createEscritor(@RequestBody escritor: EscritorRequestDto, @PathVariable("idManga")
    idManga: UUID): String{
        val mangaPerso = mangasDtos.firstOrNull { manga -> manga.id== idManga}

        if (mangaPerso != null){
            mangaPerso.escritores.add(
                EscritorDto(
                    id = UUID.randomUUID(),
                    nome = escritor.nome,
                    nascimento = escritor.nascimento,
                    genero = escritor.genero,
                )
            )
        }else{
            return "Manga não encontrado"
        }
        return "Escritor ${escritor.nome} criado com sucesso!"
    }

}