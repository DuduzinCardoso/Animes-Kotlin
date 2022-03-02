package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Criador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.transaction.Transactional

interface CriadorRepository : JpaRepository<Criador, Long> {
    @Query(value =
    """
        SELECT * FROM public.criadores
        WHERE 
        anime_id = :idAnime
        ORDER BY nome desc
        """
        , nativeQuery = true
    )
    fun findByIdAnime(idAnime: Long?): List<Criador>;

    @Modifying
    @Transactional
    @Query(value =
    """
        UPDATE public.criadores
        SET nome = :nome, nascimento = :nascimento
        WHERE id = :id
        """
        , nativeQuery = true
    )
    fun update(id: Long, nome: String, nascimento: Date);
}