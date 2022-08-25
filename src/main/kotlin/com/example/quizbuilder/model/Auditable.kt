package com.example.quizbuilder.model

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class Auditable(
    @CreatedBy
    @ManyToOne
    var createdBy: User? = null,

    @LastModifiedBy
    @ManyToOne
    var lastModifiedBy: User? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: Timestamp? = null,

    @LastModifiedDate
    var updatedAt: Timestamp? = null
)