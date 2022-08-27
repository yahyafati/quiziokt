package com.example.quizbuilder.utils

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

@JsonFilter("errorFilter")
data class ErrorResponse(
    val error: String? = "There was something wrong",
    val details: Map<String, String>? = emptyMap(),
    @JsonIgnore
    private val exception: java.lang.Exception? = null,
    val time: LocalDateTime = LocalDateTime.now(),
    var stacktrace: String? = exception?.stackTraceToString()
)