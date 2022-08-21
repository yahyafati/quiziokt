package com.example.quizbuilder.model

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Quiz (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Int,
    @Column(nullable = false)
    var title: String,
    ) : Auditable() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Quiz
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

}
