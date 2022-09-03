package com.yahya.quizbuilderkt.exception

import com.yahya.quizbuilderkt.model.Choice
import org.springframework.http.HttpStatus

class InvalidQuestionException(
    override val message: String = "",
    val currentChoices: List<Choice>,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) :
    RuntimeException(message) {

    companion object {
        fun noAnswerProvided(currentChoices: List<Choice>): InvalidQuestionException {
            return InvalidQuestionException("no answer provided", currentChoices)
        }

        fun questionHadMultipleAnswer(currentChoices: List<Choice>): InvalidQuestionException {
            return InvalidQuestionException("question has multiple answers", currentChoices)
        }

        fun multipleAnswerProvided(currentChoices: List<Choice>): InvalidQuestionException {
            return InvalidQuestionException(
                "more than one answer provided for a single-answer question",
                currentChoices
            )
        }

        fun noChoiceProvided(): InvalidQuestionException {
            return InvalidQuestionException("empty choice provided", emptyList())
        }
    }
}