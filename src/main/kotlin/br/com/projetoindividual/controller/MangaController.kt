package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.*
import org.springframework.web.bind.annotation.*
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
                editoras = ArrayList(),
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
    @DeleteMapping("/excluir/escritor/{idManga}/{idEscritor}")
    fun deleteEscritor(
        @PathVariable("idEscritor") idEscritor: UUID,
        @PathVariable("idManga") idManga: UUID
    ): String {
        mangasDtos
            .firstOrNull { mangaDto -> mangaDto.id == idManga }!!
            .escritores.removeIf { escritor -> escritor.id == idEscritor}

        return "Escritor excluído com sucesso!"
    }

    @DeleteMapping("/excluir/editora/{idManga}/{idEditora}")
    fun deleteEditora(
        @PathVariable("idEditora") idEditora: UUID,
        @PathVariable("idManga") idManga: UUID
    ): String {
        mangasDtos
            .firstOrNull { mangaDto -> mangaDto.id == idManga }!!
            .editoras.removeIf { editora -> editora.id == idEditora}

        return "Editora excluído com sucesso!"
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
        return "Escritor(a) ${escritor.nome} criado(a) com sucesso!"
    }
    @PostMapping("/create/editora/{idManga}")
    fun createEditora(@RequestBody editora: EditoraRequestDto, @PathVariable("idManga")
    idManga: UUID): String{
        val mangaEdito = mangasDtos.firstOrNull { manga -> manga.id== idManga}

        if (mangaEdito != null){
            mangaEdito.editoras.add(
                EditoraDto(
                    id = UUID.randomUUID(),
                    nome = editora.nome,
                    pais = editora.pais,
                    fundada = editora.fundada,
                )
            )
        }else{
            return "Manga não encontrado"
        }
        return "Editora ${editora.nome} criado(a) com sucesso!"
    }
    @PatchMapping("/update/{idManga}/{novoNomeEditora}/{idEditora}")
    fun updateEditora(
        @PathVariable ("novoNomeEditora")novoNomeDaEditora: String,
        @PathVariable("idEditora") idEditora: UUID,
        @PathVariable("idManga") idManga: UUID
    ): String {
        val nomeDoManga = mangasDtos.firstOrNull { manga -> manga.id == idManga }
        val nomeDaEditora = nomeDoManga!!.editoras.firstOrNull { editora-> editora.id == idEditora}

        if (nomeDaEditora != null){
            nomeDaEditora.nome = novoNomeDaEditora
        }
        else{
            return "Editora não encontrada!"
        }
        return "Editora atualizada com sucesso"
    }
}