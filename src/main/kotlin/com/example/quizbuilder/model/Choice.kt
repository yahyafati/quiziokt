package com.example.quizbuilder.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Choice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @field:NotBlank
    @Column(nullable = false)
    var text: String = "",

    var answer: Boolean = false,
    @ManyToOne
    var question: Question?
) {
    override fun toString(): String {
        return "Choice(id=$id, text='$text', answer=$answer, question=$question)"
    }

}