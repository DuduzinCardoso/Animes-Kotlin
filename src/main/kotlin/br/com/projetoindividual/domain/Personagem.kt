package br.com.projetoindividual.domain

import javax.persistence.*

@Entity
@Table(name = "personagens")
class Personagem (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "animeId", referencedColumnName = "id")
    val anime: Anime? = null,

    val nome: String? = null,
    val genero: String? = null
) {
    private constructor(): this(null, null, null, null)
}


