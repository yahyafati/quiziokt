package com.example.quizbuilder.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @CreatedDate
    var creationTime: Timestamp,
    @LastModifiedDate
    var updateTime: Timestamp
    )