package mx.omarmartinez.geoquiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true, userAnswer = false),
        Question(R.string.question_oceans, answer = true, userAnswer = false),
        Question(R.string.question_mideast, answer = false, userAnswer = false),
        Question(R.string.question_africa, answer = false, userAnswer = false),
        Question(R.string.question_americas, answer = true, userAnswer = false),
        Question(R.string.question_asia, answer = true, userAnswer = false)
    )

    private var currentIndex = 0

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
            var score = 0
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