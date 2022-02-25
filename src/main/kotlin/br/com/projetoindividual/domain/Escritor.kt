package br.com.projetoindividual.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "escritores")
class Escritor (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "manga_id", referencedColumnName = "id")
    val manga: Manga? = null,

    var nome: String? = null,
    val nascimento: Date? = null,
    val genero: Char? = null,
) {
    private constructor(): this(null, null, null, null, null)
}