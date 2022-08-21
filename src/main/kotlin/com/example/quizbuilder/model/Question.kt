package com.example.quizbuilder.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
@JsonFilter("QuestionFilter")
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @Column(nullable = false)
    var text: String,
    var multi: Boolean,

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var quiz: Quiz?
) : Auditable(), IModel {


}