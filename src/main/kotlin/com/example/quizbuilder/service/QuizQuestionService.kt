package com.example.quizbuilder.service

import com.example.quizbuilder.exception.ResourceNotFoundException
import com.example.quizbuilder.model.Question
import com.example.quizbuilder.model.Quiz
import org.springframework.stereotype.Service

@Service
class QuizQuestionService(val quizService: IQuizService, val questionService: IQuestionService) : IQuizQuestionService {

    override fun getQuestions(quizId: Int): List<Question> {
        if (!quizService.exists(quizId)) throw ResourceNotFoundException.createWith("quiz", quizId)
        return questionService.findQuestionsByQuizId(quizId)
    }

    override fun getQuestion(quizId: Int, questionId: Int): Question {
        return questionService.findQuestionByQuizAndId(quizId, questionId)
    }

    override fun saveQuestion(quizId: Int, question: Question): Question {
        question.quiz = Quiz(id=quizId)
        return questionService.save(question)
    }

    override fun updateQuestion(quizId: Int, questionId: Int, question: Question): Question {
        question.id = questionId
        question.quiz = Quiz(id=quizId)
        return questionService.update(question)
    }

    override fun deleteQuestion(quizId: Int, questionId: Int) {
        return questionService.deleteByQuizAndId(quizId, questionId)
    }
}