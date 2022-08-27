package com.example.quizbuilder.model

import org.hibernate.Hibernate
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Quiz(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int = 0,
    @Column(nullable = false)
    @field:NotBlank(message = "Quiz title can't be blank")
    var title: String? = "",
    var description: String? = ""
) : Auditable(), IModel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Quiz
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString(): String {
        return "Quiz(id=$id, title=$title, description=$description)"
    }


}
