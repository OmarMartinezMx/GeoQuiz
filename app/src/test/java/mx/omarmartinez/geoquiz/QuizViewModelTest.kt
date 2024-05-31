package mx.omarmartinez.geoquiz

import mx.omarmartinez.geoquiz.viewmodel.QuizViewModel
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class QuizViewModelTest{

    @Test
    fun providesExpectedQuestionText(){
        val quizViewModel = QuizViewModel()

        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun movesToNextQuestion(){
        val quizViewModel = QuizViewModel()

        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
        quizViewModel.nextQuestion()

        assertEquals(R.string.question_oceans, quizViewModel.currentQuestionText)
    }

    @Test
    fun movesToPrevQuestion(){
        val quizViewModel = QuizViewModel()

        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
        quizViewModel.prevQuestion()
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
    }

    @Test
    fun checkNoPartialScoreIsCalculated(){
        val quizViewModel = QuizViewModel()

        val firstQuestion = quizViewModel.currentQuestion
        firstQuestion.userAnswer = false

        assertEquals(-1, quizViewModel.checkScore())
    }

    @Test
    fun checkScoreCalculation(){
        val quizViewModel = QuizViewModel()

        //answers questions
        quizViewModel.currentQuestion.userAnswer = true
        quizViewModel.nextQuestion()

        quizViewModel.currentQuestion.userAnswer = true
        quizViewModel.nextQuestion()

        quizViewModel.currentQuestion.userAnswer = false
        quizViewModel.nextQuestion()

        quizViewModel.currentQuestion.userAnswer = false
        quizViewModel.nextQuestion()

        quizViewModel.currentQuestion.userAnswer = true
        quizViewModel.nextQuestion()

        quizViewModel.currentQuestion.userAnswer = true


        assertEquals(66, quizViewModel.checkScore())
    }

    @Test
    fun checkCorrectCurrentQuestionAnswer(){
        val quizViewModel = QuizViewModel()

        assertTrue(quizViewModel.currentQuestionAnswer)
    }
}