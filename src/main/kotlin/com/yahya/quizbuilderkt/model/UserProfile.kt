package com.yahya.quizbuilderkt.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp
import javax.persistence.*

@Entity
class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    var bio: String? = null


    @OneToOne
    var user: User? = null

    @CreatedDate
    var creationTime: Timestamp? = null

    @LastModifiedDate
    var updateTime: Timestamp? = null
}