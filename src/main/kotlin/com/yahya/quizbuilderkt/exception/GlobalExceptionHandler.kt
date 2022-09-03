package com.yahya.quizbuilderkt.exception

import com.yahya.quizbuilderkt.utils.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.util.WebUtils


@ControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val LOG = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(
        ResourceNotFoundException::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        InvalidQuestionException::class,
        UsernameTakenException::class,
        AccessDeniedException::class,
        QuizAlreadyPublished::class
    )
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val headers = HttpHeaders()
        return when (ex) {
            is ResourceNotFoundException -> {
                LOG.warn("{}: {}", ex.javaClass.name, ex.message)
                handleResourceNotFoundException(ex, headers)
            }
            is MethodArgumentNotValidException -> {
                LOG.warn(
                    "{}: {} {}",
                    ex.javaClass.name,
                    ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" },
                    ex.message
                )
                handleMethodArgumentNotValidException(ex, headers)
            }
            is InvalidQuestionException -> {
                LOG.warn("{}: {}", ex.javaClass.name, ex.message)
                val status = HttpStatus.BAD_REQUEST
                handleInvalidQuestionException(ex, headers, status)
            }
            is AccessDeniedException -> {
                LOG.warn("{}: {}", ex.javaClass.name, ex.message)
                val status = HttpStatus.FORBIDDEN
                handleAccessDeniedException(ex, headers, status)
            }
//            is MissingServletRequestParameterException -> {
//                val status = HttpStatus.BAD_REQUEST
//                handleMissingServletRequestParameterException(ex, headers, status)
//            }
            is UsernameTakenException -> {
                LOG.warn("{} {}", ex.javaClass.name, ex.message)
                val status = HttpStatus.BAD_REQUEST
                handleUsernameTakenException(ex, headers, status)
            }
            is QuizAlreadyPublished -> {
                LOG.warn("{} {}", ex.javaClass.name, ex.message)
                val status = HttpStatus.CONFLICT
                handleQuizAlreadyPublished(ex, headers, status)
            }
            else -> {
                LOG.error("{} {}", ex.javaClass.name, ex.message)
                val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                handleExceptionInternal(ex, ErrorResponse(ex.message), headers, status, request)
            }
        }
    }

    private fun handleQuizAlreadyPublished(
        ex: QuizAlreadyPublished,
        headers: HttpHeaders,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status).headers(headers).body(ErrorResponse(ex.message, exception = ex))
    }

    private fun handleAccessDeniedException(
        ex: AccessDeniedException,
        headers: HttpHeaders,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status).headers(headers).body(ErrorResponse(ex.message, exception = ex))
    }

    private fun handleUsernameTakenException(
        ex: UsernameTakenException,
        headers: HttpHeaders,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status).headers(headers).body(ErrorResponse(ex.message, exception = ex))
    }

    private fun handleInvalidQuestionException(
        ex: InvalidQuestionException,
        headers: HttpHeaders,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status).headers(headers)
            .body(ErrorResponse(ex.message, details = hashMapOf("currentChoices" to ex.currentChoices), exception = ex))
    }

    private fun handleMissingServletRequestParameterException(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        val message = "No ${ex.parameterName} of type ${ex.parameterType} provided"
        return ResponseEntity.status(status).headers(headers).body(ErrorResponse(message, exception = ex))
    }

    private fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders
    ): ResponseEntity<ErrorResponse> {
        val errorMap: HashMap<String, String> = HashMap()
        exception.bindingResult.fieldErrors.forEach {
            errorMap[it.field] = it.defaultMessage ?: "No error message provided for this validation"
        }
        val errorResponse = ErrorResponse("invalid form submitted", errorMap, exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errorResponse)
    }

    private fun handleResourceNotFoundException(
        exception: ResourceNotFoundException,
        headers: HttpHeaders,
    ): ResponseEntity<ErrorResponse> {
        val status = exception.status
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