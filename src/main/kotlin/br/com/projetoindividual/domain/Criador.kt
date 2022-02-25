package br.com.projetoindividual.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "criadores")
class Criador (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "anime_id", referencedColumnName = "id")
    val anime: Anime? = null,

    var nome: String? = null,
    val nascimento: Date? = null,
) {
    private constructor(): this(null, null, null, null )
}