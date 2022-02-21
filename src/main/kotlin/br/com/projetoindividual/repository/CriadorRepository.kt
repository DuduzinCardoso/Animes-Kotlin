package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Criador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

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
}