package br.com.projetoindividual.controller

import br.com.projetoindividual.dto.*
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/mangas")
class MangaController (
    var mangasDtos: ArrayList<MangaDto>
) {
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
        var mensagemManga = "Manga excluído com sucesso!"
        val manga = mangasDtos.firstOrNull { manga -> manga.id == nomeManga }

        if(manga != null){
            mangasDtos.removeIf { manga -> manga.id ==nomeManga }
        }else{
            mensagemManga = "Manga não encontrado!"
        }

        return mensagemManga
    }

    @DeleteMapping("/excluir/escritor")
    fun deleteEscritor(@RequestBody dadosEscritor: DeleteEscritorRequestDto): String {

        val manga = mangasDtos.firstOrNull { manga -> manga.id == dadosEscritor.idManga }
        var mensagemEscritor = "Escritor(a) excluído com sucesso!"

        if (manga != null){
            val escritor = manga.escritores.firstOrNull { escritor-> escritor.id == dadosEscritor.idEscritor }

            if (escritor!= null) {
                manga.escritores.removeIf { escritor -> escritor.id == dadosEscritor.idEscritor}
            }
            else{
                mensagemEscritor = "Escritor(a) não encontrado!"
            }
        } else{
            mensagemEscritor = "Manga não encontrado!"
        }

        return mensagemEscritor
    }

    @DeleteMapping("/excluir/editora")
    fun deleteEditora(@RequestBody dadosEditora: DeleteEditoraRequestDto): String {

        val manga = mangasDtos.firstOrNull { manga -> manga.id == dadosEditora.idManga }
        var mensagemEditora = "Editora excluída com sucesso!"

        if (manga != null){
            val editora = manga.editoras.firstOrNull { editora -> editora.id == dadosEditora.idEditora }

            if (editora != null) {
                manga.editoras.removeIf { editora -> editora.id == dadosEditora.idEditora}
            }
            else{
                mensagemEditora = "Editora não encontrada!"
            }
        } else{
            mensagemEditora = "Manga não encontrado!"
        }

        return mensagemEditora
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
        val mangaEditora = mangasDtos.firstOrNull { manga -> manga.id== idManga}

        if (mangaEditora != null){
            mangaEditora.editoras.add(
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

    @PatchMapping("/update/editora")
    fun updateEditora(@RequestBody dadosEditora: UpdateEditoraRequestDto): String {
        var mensagemEditora = "Editora atualizada com sucesso"
        val manga = mangasDtos.firstOrNull { manga -> manga.id == dadosEditora.idManga }

        if (manga != null){
            val editora = manga.editoras.firstOrNull { editora-> editora.id == dadosEditora.idEditora}

            if (editora != null){
                editora.nome = dadosEditora.nome
            }
            else{
                mensagemEditora = "Editora não encontrada!"
            }
        } else{
            mensagemEditora = "Manga não encontrado!"
        }

        return mensagemEditora
    }

    @PatchMapping("/update/escritor")
    fun updateEscritor(@RequestBody dadosEscritor: UpdateEscritorRequestDto): String {
        var mensagemEscritor = "Escritor(a) atualizado(a) com sucesso"
        val manga = mangasDtos.firstOrNull { manga -> manga.id == dadosEscritor.idManga }

        if (manga != null) {
            val escritor = manga.escritores.firstOrNull { escritor -> escritor.id == dadosEscritor.idEscritor }

            if (escritor != null) {
                escritor.nome = dadosEscritor.nome
            }
            else {
                mensagemEscritor = "Escritor(a) não encontrada!"
            }
        } else {
            mensagemEscritor = "Manga não encontrado!"
        }

        return mensagemEscritor
    }
}