package com.example.quizbuilder.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    @CreatedDate
    var creationTime: Timestamp,
    @LastModifiedDate
    var updateTime: Timestamp
) {
    override fun toString(): String {
        return "User(id=$id)"
    }
}