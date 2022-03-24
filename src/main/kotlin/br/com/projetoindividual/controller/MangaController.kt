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

@CrossOrigin("*")
@RestController
@RequestMapping("/manga")
class MangaController (
    val mangaRepository: MangaRepository,
    val editoraRepository: EditoraRepository,
    val escritorRepository: EscritorRepository,
) {
    @GetMapping("/mangas")
    fun getListMangas(): ResponseEntity<List<MangaDto>> {
        try {
            return ResponseEntity.ok(mangaRepository.findAll()
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
                })
        }catch (exception: Exception){
            return ResponseEntity.notFound().build()
        }

    }

    @GetMapping("{id}")
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

    @GetMapping("/create")
    fun createManga(@RequestBody manga: MangaRequestDto): String {
        var mensagem = "Manga ${manga.nome.trim()} criado com sucesso!"

        try{
            mangaRepository.save(Manga(nome = manga.nome.trim()))
        } catch (exception: Exception) {
            mensagem = "Falha ao cadastrar manga!"
        }

        return mensagem
    }

    @DeleteMapping("/{idManga}")
    fun deleteManga(@PathVariable("idManga") idManga: Long): String? {
        var mensagem = "Manga deletado com sucesso!"

        try {
            mangaRepository.deleteById(idManga)
        } catch (exception: Exception) {
            mensagem = "Falha ao deletar manga, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/editora/{idEditora}")
    fun deleteEditora(@PathVariable idEditora: Long): String? {
        var mensagem = "Editora deletado com sucesso!"

        try {
            editoraRepository.deleteById(idEditora)
        } catch (exception: Exception){
            mensagem = "Falha ao deletar editora, por favor, tente novamente!"
        }

        return mensagem
    }

    @DeleteMapping("/escritor/{idEscritor}")
    fun deleteEscritor(@PathVariable idEscritor: Long): String {
        var mensagem = "Escritor(a) deletado com sucesso!"

        try {
            escritorRepository.deleteById(idEscritor)
        } catch (exception: Exception){
            mensagem = "Falha ao deletar escritor, por favor, tente novamente!"
        }

        return mensagem
    }

    @PostMapping("/escritor")
    fun createEscritor(
        @RequestBody escritor: EscritorRequestDto): String{
        var mensagem = "Escritor ${escritor.nome} criado com sucesso!"

        try {
            val manga = mangaRepository.getById(escritor.idManga)

            if (manga != null){
                escritorRepository.save(
                    Escritor(
                        manga = manga,
                        nome = escritor.nome,
                        nascimento = escritor.nascimento,
                        genero = escritor.genero,
                    )
                )
            }
        } catch (exception: Exception){
            mensagem = "Falha ao cadastrar escritor!"
        }

        return mensagem
    }

    @PostMapping("/editora")
    fun createEditora(@RequestBody editora: EditoraRequestDto
    ): String{
      var mensagem = "Editora ${editora.nome} criado com sucesso!"

      try {
          val manga = mangaRepository.getById(editora.idManga)

          if (manga != null){
              editoraRepository.save(
                  Editora(
                      manga = manga,
                      nome = editora.nome,
                      pais = editora.pais,
                      fundada = editora.fundada
                  )
              )
          }
      } catch (exception: Exception){
          mensagem = "Falha ao cadastrar editora!"
      }
        return mensagem
    }

    @PutMapping("/editora")
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
    @PutMapping("/escritor")
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

