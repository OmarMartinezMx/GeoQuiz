package mx.omarmartinez.geoquiz.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import mx.omarmartinez.geoquiz.Question
import mx.omarmartinez.geoquiz.R

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true, userAnswer = false),
        Question(R.string.question_oceans, answer = true, userAnswer = false),
        Question(R.string.question_mideast, answer = false, userAnswer = false),
        Question(R.string.question_africa, answer = false, userAnswer = false),
        Question(R.string.question_americas, answer = true, userAnswer = false),
        Question(R.string.question_asia, answer = true, userAnswer = false)
    )

    var isCheater: Boolean
        get() = savedStateHandle[IS_CHEATER_KEY] ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestion: Question
        get() = questionBank[currentIndex]

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun nextQuestion(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun prevQuestion(){
        if(currentIndex == 0){
            currentIndex = questionBank.size - 1
        } else {
            currentIndex--
        }
    }

    fun checkScore(): Int{
        return if(currentIndex == questionBank.size -1){
            var correctAnswers = 0
            for (item in questionBank){
                if(item.userAnswer){
                    correctAnswers++
                }
            }

            ((correctAnswers.toDouble() / questionBank.size) * 100).toInt()
        } else{
            -1
        }
    }
}