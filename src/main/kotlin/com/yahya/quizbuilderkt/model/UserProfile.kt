package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var name: String? = null
    var bio: String? = null


    @OneToOne(optional = false)
    @JsonIgnore
    var user: User? = null

    @get:Transient
    val username: String? get() = user?.username

    @get:Transient
    val email: String? get() = user?.email

    @CreatedDate
    var creationTime: Timestamp? = null

    @LastModifiedDate
    var updateTime: Timestamp? = null
}