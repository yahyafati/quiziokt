package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class Auditable(
    @CreatedBy
    @ManyToOne
    @JsonIgnore
    @JoinColumn(updatable = false)
    var createdBy: User? = null,
    @LastModifiedBy
    @ManyToOne
    @JsonIgnore
    var lastModifiedBy: User? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: Timestamp? = null,

    @LastModifiedDate
    var updatedAt: Timestamp? = null
) {
    @JsonProperty("createdBy")
    @Transient
    fun getCreator(): String? {
        return this.createdBy?.username
    }

    @JsonProperty("lastModifiedBy")
    @Transient
    fun getLastModifiedBy(): String? {
        return this.lastModifiedBy?.username
    }
}