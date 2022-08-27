package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonFilter
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@JsonFilter("QuestionFilter")
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    @Column(nullable = false)
    @field:NotBlank(message = "Question text can't be empty")
    var text: String? = null,
    var multi: Boolean = false,
    @ManyToOne
    var quiz: Quiz? = null
) : Auditable(), IModel {

    override fun toString(): String {
        return "Question(id=$id, text='$text', multi=$multi)"
    }
}