package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@EntityListeners(AuditingEntityListener::class)
@JsonFilter("userFilter")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    @Column(unique = true)
    @field:NotBlank
    private var username: String,
    @field:NotBlank
    @JsonIgnore
    private var password: String,
    @CreatedDate
    var creationTime: Timestamp,
    @LastModifiedDate
    var updateTime: Timestamp
) : UserDetails {

    //    var version: Int = 0
    @JsonIgnore
    private var accountNonExpired: Boolean = true

    @JsonIgnore
    private var accountNonLocked: Boolean = true

    @JsonIgnore
    private var credentialsNonExpired: Boolean = true

    @JsonIgnore
    private var enabled: Boolean = true

    override fun toString(): String {
        return "User(id=$id)"
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }


    override fun isAccountNonExpired(): Boolean {
        return this.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return this.accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return this.credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return this.enabled
    }
}