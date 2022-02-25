package br.com.projetoindividual.controller

import br.com.projetoindividual.domain.Editora
import br.com.projetoindividual.domain.Escritor
import br.com.projetoindividual.domain.Manga
import br.com.projetoindividual.dto.*
import br.com.projetoindividual.repository.EditoraRepository
import br.com.projetoindividual.repository.EscritorRepository
import br.com.projetoindividual.repository.MangaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("/mangas")
class MangaController (
    val mangaRepository: MangaRepository,
    val editoraRepository: EditoraRepository,
    val escritorRepository: EscritorRepository,
) {
    @GetMapping("/get-mangas")
    fun getListMangas(): List<MangaDto> {

        return mangaRepository.findAll()
            .map { manga ->
                MangaDto(
                    id = manga.id,
                    nome = manga.nome,
                    editoras = editoraRepository.findByIdManga(manga.id)
                        .map { editora ->
                            EditoraDto(
                                id = editora.id,
                                nome = editora.nome,
                                pais = editora.pais,
                                fundada = editora.fundada
                            )
                        },
                    escritores = escritorRepository.findByIdManga(manga.id)
                        .map { escritor ->
                            EscritorDto(
                                id = escritor.id,
                                nome = escritor.nome,
                                nascimento = escritor.nascimento,
                                genero = escritor.genero
                            )
                        }
                )
            }
    }

    @GetMapping("/info/manga/{id}")
    fun getManga(@PathVariable("id") id: Long): ResponseEntity<MangaDto> {
        try {
            val manga = mangaRepository.getById(id)

            if (manga != null) {
                return ResponseEntity.ok(
                    MangaDto(
                        id = manga.id,
                        nome = manga.nome,
                        editoras = editoraRepository.findByIdManga(manga.id)
                            .map { editora ->
                                EditoraDto(
                                    id = editora.id,
                                    nome = editora.nome,
                                    pais = editora.pais,
                                    fundada = editora.fundada,
                                )
                            },
                        escritores = escritorRepository.findByIdManga(manga.id)
                            .map { escritor ->
                                EscritorDto(
                                    id = escritor.id,
                                    nome = escritor.nome,
                                    nascimento = escritor.nascimento,
                                    genero = escritor.genero,
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

    @PostMapping("/manga/create")
    fun createManga(@RequestBody manga: MangaRequestDto): String {
        mangaRepository.save(Manga(nome = manga.nome.trim()))

        return "Manga ${manga.nome.trim()} criado com sucesso!"
    }

    @DeleteMapping("/deletar/manga/{idManga}")
    fun deleteManga(@PathVariable("idManga") idManga: Long): String? {
        var mensagem = "Manga deletado com sucesso!"
        try {
            mangaRepository.deleteById(idManga)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar manga, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/deletar/editora/{idEditora}")
    fun deleteEditora(@PathVariable("idEditora") idEditora: Long): String? {
        var mensagem = "Editora deletado com sucesso!"
        try {
            editoraRepository.deleteById(idEditora)
        } catch (exception: Exception){
            mensagem = "Falha ao deletar editora, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/deletar/escritor/{idEscritor}")
    fun deleteEscritor(@PathVariable("idEscritor") idEscritor: Long): String? {
        var mensagem = "Escritor(a) deletado com sucesso!"
        try {
            escritorRepository.deleteById(idEscritor)
        } catch (exception: Exception){
            mensagem = "Falha ao deletar escritor, por favor, tente novamente!"
        }

        return mensagem
    }

    @PostMapping("/create/escritor/{idManga}")
    fun createEscritor(
        @RequestBody escritor: EscritorRequestDto, @PathVariable("idManga")
        idManga: Long): String{
        var mensagem = "Manga não encontrado!"

        try {
            val manga = mangaRepository.getById(idManga)

            if (manga != null){
                escritorRepository.save(
                    Escritor(
                        manga = manga,
                        nome = escritor.nome,
                        nascimento = escritor.nascimento,
                        genero = escritor.genero,
                    )
                )
                mensagem = "Escritor(a) ${escritor.nome} criado com sucesso!"
            }
        } catch (exception: Exception){
            println(exception.message)
        }

        return mensagem
    }

    @PostMapping("/create/editora/{idManga}")
    fun createEditora(@RequestBody editora: EditoraRequestDto, @PathVariable("idManga")
    idManga: Long): String{
      var mensagem = "Manga não encontrado"

      try {
          val manga = mangaRepository.getById(idManga)

          if (manga != null){
              editoraRepository.save(
                  Editora(
                      manga = manga,
                      nome = editora.nome,
                      pais = editora.pais,
                      fundada = editora.fundada
                  )
              )
              mensagem = "Editora ${editora.nome} criado com sucesso!"
          }
      } catch (exception: Exception){
          println(exception.message)
      }
        return mensagem
    }

    @PatchMapping("/update/editora")
    fun updateEditora(@RequestBody dadosEditora: UpdateEditoraRequestDto): String{
        var mensagem = "Editora atualizada com sucesso!"

        try {
            editoraRepository.update(
                dadosEditora.id,
                dadosEditora.nome,
                dadosEditora.pais,
                dadosEditora.fundada
            )
        } catch (exception: Exception){
            mensagem = "Falha ao atualizar editora:  ${exception.message}!"
            println(exception)
        }
        return mensagem
    }
        @PatchMapping("/update/escritor")
    fun updateEscritor(@RequestBody dadosEscritor: UpdateEscritorRequestDto): String {
        var mensagem = "Escritor atualizado com sucesso!"

        try {
            escritorRepository.update(
                dadosEscritor.id,
                dadosEscritor.nome,
                dadosEscritor.nascimento,
                dadosEscritor.genero
            )
        } catch (exception: Exception){
            mensagem = "Falha ao atualizar escritor: ${exception.message}"
            println(exception)
        }
        return mensagem
    }
}

