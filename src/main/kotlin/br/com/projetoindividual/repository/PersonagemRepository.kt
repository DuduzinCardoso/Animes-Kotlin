package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Personagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PersonagemRepository : JpaRepository<Personagem, Long> {
    @Query(value =
        """
        SELECT * FROM public.personagens
        WHERE 
        anime_id = :idAnime
        ORDER BY nome desc
        """
        , nativeQuery = true
    )
    fun findByIdAnime(idAnime: Long?): List<Personagem>;
}