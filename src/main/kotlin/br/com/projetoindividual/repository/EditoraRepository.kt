package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Editora
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Date
import javax.transaction.Transactional

interface EditoraRepository : JpaRepository<Editora, Long> {
    @Query(value=
    """
        SELECT * FROM public.editoras
        WHERE 
        manga_id = :idManga
        ORDER BY nome desc
    """
        , nativeQuery = true
    )
    fun findByIdManga(idManga: Long?): List<Editora>;

    @Modifying
    @Transactional
    @Query(value =
    """
        UPDATE public.editoras
        SET nome = :nome, pais = :pais, fundada = :fundada
        WHERE id = :id
        """
        , nativeQuery = true
    )
    fun update(id: Long, nome: String, pais: String, fundada: Date);

}