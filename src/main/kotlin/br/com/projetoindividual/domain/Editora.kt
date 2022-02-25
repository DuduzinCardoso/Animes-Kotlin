package br.com.projetoindividual.domain

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "editoras")
class Editora (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "manga_id", referencedColumnName = "id")
    val manga: Manga? = null,

    var nome: String? = null,
    var pais: String? = null,
    val fundada: Date? = null,
) {
    private constructor(): this(null, null, null, null, null )
}



