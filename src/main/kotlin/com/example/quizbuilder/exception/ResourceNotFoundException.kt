package com.example.quizbuilder.exception

data class ResourceNotFoundException(override val message: String = "") : RuntimeException(message)