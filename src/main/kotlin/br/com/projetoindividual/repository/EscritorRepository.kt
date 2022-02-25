package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Escritor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

interface EscritorRepository : JpaRepository<Escritor, Long> {
    @Query(value =
    """
        SELECT * FROM public.escritores
        WHERE
        manga_id = :idManga
        ORDER BY nome desc
    """
        , nativeQuery = true
        )
    fun findByIdManga(idManga: Long?): List<Escritor>;

    @Modifying
    @Transactional
    @Query(value =
    """
        UPDATE public.escritores
        SET nome = :nome, nascimento = :nascimento, genero = :genero
        WHERE id = :id
        """
        , nativeQuery = true
    )
    fun update(id: Long, nome: String, nascimento: Date, genero: Char);
}