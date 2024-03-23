package mx.omarmartinez.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import mx.omarmartinez.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, answer = true, userAnswer = false),
        Question(R.string.question_oceans, answer = true, userAnswer = false),
        Question(R.string.question_mideast, answer = false, userAnswer = false),
        Question(R.string.question_africa, answer = false, userAnswer = false),
        Question(R.string.question_americas, answer = true, userAnswer = false),
        Question(R.string.question_asia, answer = true, userAnswer = false)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true, view)
            changeResponseButtonState(false)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false, view)
            changeResponseButtonState(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size

            updateQuestion()
        }

        binding.prevButton.setOnClickListener {
            if(currentIndex == 0){
                currentIndex = questionBank.size - 1
            } else {
                currentIndex--
            }

            updateQuestion()
        }

        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean, view: View){
        val question = questionBank[currentIndex]
        val correctAnswer = question.answer
        val messageResId: Int

        if(userAnswer == correctAnswer){
            messageResId = R.string.correct_toast
            question.userAnswer = true
        } else{
            messageResId = R.string.incorrect_toast
            question.userAnswer = false
        }

        Snackbar.make(view, messageResId, Snackbar.LENGTH_LONG).show()

        if(currentIndex == questionBank.size -1){
            var score = 0
            var correctAnswers = 0
            for (item in questionBank){
                if(item.userAnswer){
                    correctAnswers++
                }
            }

            score = ((correctAnswers.toDouble() / questionBank.size) * 100).toInt()
            val scoreMessage = getString(R.string.score_message, score)

            Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun changeResponseButtonState(enable: Boolean){
        binding.trueButton.isEnabled = enable
        binding.falseButton.isEnabled = enable
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        changeResponseButtonState(true)
    }
}