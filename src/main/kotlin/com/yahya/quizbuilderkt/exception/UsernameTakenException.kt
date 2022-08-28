package com.yahya.quizbuilderkt.exception

class UsernameTakenException(message: String) : RuntimeException(message) {

    companion object {
        fun createWith(username: String): UsernameTakenException {
            return UsernameTakenException("the username: $username is already taken")
        }
    }
}