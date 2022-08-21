package com.example.quizbuilder.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @Column(nullable = false)
    var text: String,
    var multi: Boolean,

    @ManyToOne
    var quiz: Quiz?
) : Auditable(), IModel {


}