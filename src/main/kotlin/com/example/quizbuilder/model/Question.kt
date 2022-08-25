package com.example.quizbuilder.model

import com.fasterxml.jackson.annotation.JsonFilter
import javax.persistence.*

@Entity
@JsonFilter("QuestionFilter")
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    @Column(nullable = false)
    var text: String,
    var multi: Boolean,
    @ManyToOne
    var quiz: Quiz?
) : Auditable(), IModel {

    override fun toString(): String {
        return "Question(id=$id, text='$text', multi=$multi)"
    }
}