package com.yahya.quizbuilderkt.security

import com.yahya.quizbuilderkt.model.Auditable
import com.yahya.quizbuilderkt.model.User
import org.springframework.security.core.Authentication

interface IAuthenticationFacade {
    companion object {
        fun equals(username1: String, username2: String): Boolean {
            return username1.equals(username2, ignoreCase = true)
        }

    }

    fun equalsAuth(username1: String): Boolean {
        return equals(username1, username)
    }

    fun equalsAuth(user: User?): Boolean {
        return equals(user?.username!!, username)
    }

    fun equalsAuth(auditable: Auditable?): Boolean {
        return equalsAuth(auditable?.createdBy)
    }

    val authentication: Authentication?

    val username: String
        get() = authentication!!.name


}