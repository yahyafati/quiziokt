package com.example.quizbuilder.exception

import org.springframework.http.HttpStatus

data class ResourceNotFoundException(override val message: String = "", val status: HttpStatus = HttpStatus.NOT_FOUND) : RuntimeException(message) {
    companion object {

        fun createWith(name: String, status: HttpStatus = HttpStatus.NOT_FOUND): ResourceNotFoundException {
            return ResourceNotFoundException("no $name exists with given details", status)
        }

        fun createWith(name: String, id: Int, status: HttpStatus = HttpStatus.NOT_FOUND): ResourceNotFoundException {
            return ResourceNotFoundException("no $name exists with the id $id", status)
        }
    }
}