package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.Hibernate
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@JsonFilter("QuizFilter")
class Quiz(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int = 0,
    @Column(nullable = false)
    @field:NotBlank(message = "Quiz title can't be blank")
    var title: String = "",
    var description: String? = null,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var permalink: String? = null,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var published: Boolean = false,
    val shufflable: Boolean = false,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "quiz")
    val questions: List<Question> = emptyList()
) : Auditable(), IModel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Quiz
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString(): String {
        return "Quiz(id=$id, title=$title, description=$description, permalink=$permalink, published=$published, shufflable=$shufflable, questions=${questions.size})"
    }


}
