package com.example.quizbuilder.exception

import com.example.quizbuilder.utils.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.util.WebUtils


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(*[ResourceNotFoundException::class])
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val headers = HttpHeaders()
        return if (ex is ResourceNotFoundException) {
            handleResourceNotFoundException(ex, headers)
        } else {
            val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
            handleExceptionInternal(ex, ErrorResponse(ex.message), headers, status, request)
        }
    }

    private fun handleResourceNotFoundException(exception: ResourceNotFoundException, headers: HttpHeaders, ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        return ResponseEntity<ErrorResponse>(ErrorResponse(exception.message), headers, status)
    }

    protected fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: ErrorResponse,
        headers: HttpHeaders?,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST)
        }
        return ResponseEntity<ErrorResponse>(body, headers, status)
    }

}