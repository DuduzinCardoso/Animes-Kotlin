package br.com.projetoindividual.domain

import javax.persistence.*

@Entity
@Table(name = "mangas")
class Manga (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "nome")
    val nome: String,
){
    private constructor(): this(null, "")
}
