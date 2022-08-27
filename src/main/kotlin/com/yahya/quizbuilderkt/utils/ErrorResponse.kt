package com.yahya.quizbuilderkt.utils

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import java.time.LocalDateTime

@JsonFilter("errorFilter")
data class ErrorResponse(
    val error: String? = "There was something wrong",
    val details: Map<String, String>? = null,
    @JsonIgnore
    private val exception: java.lang.Exception? = null,
    val timestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
    var stacktrace: String? = exception?.stackTraceToString()
)