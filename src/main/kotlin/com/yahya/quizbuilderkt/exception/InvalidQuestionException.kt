package com.yahya.quizbuilderkt.exception

import com.yahya.quizbuilderkt.model.Choice
import com.yahya.quizbuilderkt.utils.InvalidQuestionExceptionType
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

        fun typeToException(type: InvalidQuestionExceptionType, choices: List<Choice>): InvalidQuestionException? {
            return when (type) {
                InvalidQuestionExceptionType.NO_ANSWER_PROVIDED -> noAnswerProvided(choices)
                InvalidQuestionExceptionType.NO_CHOICE_PROVIDED -> noChoiceProvided()
                InvalidQuestionExceptionType.MULTIPLE_ANSWER_PROVIDED -> multipleAnswerProvided(choices)
                InvalidQuestionExceptionType.QUESTION_HAD_MULTIPLE_ANSWERS -> questionHadMultipleAnswer(choices)
                InvalidQuestionExceptionType.VALID_QUESTION -> null
            }
        }

        private val map = mapOf<InvalidQuestionExceptionType, (choices: List<Choice>) -> InvalidQuestionException>(
            InvalidQuestionExceptionType.NO_ANSWER_PROVIDED to { noAnswerProvided(it) },
            InvalidQuestionExceptionType.QUESTION_HAD_MULTIPLE_ANSWERS to { questionHadMultipleAnswer(it) },
            InvalidQuestionExceptionType.NO_CHOICE_PROVIDED to { noChoiceProvided() },
            InvalidQuestionExceptionType.MULTIPLE_ANSWER_PROVIDED to { multipleAnswerProvided(it) }
        )
    }
}