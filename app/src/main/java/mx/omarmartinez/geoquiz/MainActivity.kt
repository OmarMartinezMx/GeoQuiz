package mx.omarmartinez.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import mx.omarmartinez.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true, view)
            changeResponseButtonState(false)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false, view)
            changeResponseButtonState(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.nextQuestion()

            updateQuestion()
        }

        binding.prevButton.setOnClickListener {
            quizViewModel.prevQuestion()

            updateQuestion()
        }

        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean, view: View){
        val question = quizViewModel.currentQuestion
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

        val score = quizViewModel.checkScore()

        if(score >= 0){
            val scoreMessage = getString(R.string.score_message, score)

            Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun changeResponseButtonState(enable: Boolean){
        binding.trueButton.isEnabled = enable
        binding.falseButton.isEnabled = enable
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)

        changeResponseButtonState(true)
    }
}