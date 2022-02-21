package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Anime
import org.springframework.data.jpa.repository.JpaRepository

interface AnimeRepository : JpaRepository<Anime, Long> {}