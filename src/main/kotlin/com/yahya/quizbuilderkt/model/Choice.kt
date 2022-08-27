package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var answer: Boolean = false,

    @ManyToOne
    @JsonIgnore
    var question: Question?,
) : IModel {

    override fun toString(): String {
        return "Choice(id=$id, text='$text', answer=$answer, question=$question)"
    }

}