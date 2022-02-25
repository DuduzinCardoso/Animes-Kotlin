package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Personagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

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

    @Modifying
    @Transactional
    @Query(value =
    """
        UPDATE public.personagens
        SET nome = :nome, genero = :genero
        WHERE id = :id
        """
        , nativeQuery = true
    )
    fun update(id: Long, nome: String, genero: String);
}