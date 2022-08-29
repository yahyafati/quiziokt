package com.yahya.quizbuilderkt.model

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.yahya.quizbuilderkt.security.SecurityConstants
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size


@Entity
@EntityListeners(AuditingEntityListener::class)
@JsonFilter("UserFilter")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    var id: Int = 0,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "user")
    @JsonIgnore
    private var profile: UserProfile? = null,

    @CreatedDate
    var creationTime: Timestamp? = null,
    @LastModifiedDate
    var updateTime: Timestamp? = null
) : UserDetails {

    @Column(unique = true)
    @field:NotBlank
    @JvmField
    var username: String = ""

    @field:Email
    @Column(unique = true)
    var email: String = ""

    @field:NotBlank
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = SecurityConstants.PASSWORD_REGEX,
        message = "Password must have at least one uppercase, one lowercase, one number character"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JvmField
    var password: String = ""

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

    @JsonIgnore
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