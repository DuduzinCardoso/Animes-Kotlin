package br.com.projetoindividual.repository

import br.com.projetoindividual.domain.Manga
import org.springframework.data.jpa.repository.JpaRepository

interface MangaRepository:JpaRepository<Manga, Long> {}