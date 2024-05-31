package mx.omarmartinez.geoquiz

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import mx.omarmartinez.geoquiz.databinding.ActivityMainBinding
import mx.omarmartinez.geoquiz.viewmodel.QuizViewModel

private const val TAG = "MainActivity"
private val EXTRA_ANSWER_SHOW = "mx.omarmartinez.geoquiz.answer_show"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        it -> if(it.resultCode == Activity.RESULT_OK){
        quizViewModel.isCheater = it.data?.getBooleanExtra(EXTRA_ANSWER_SHOW, false) ?: false
    }
    }

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

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean, view: View){
        val question = quizViewModel.currentQuestion
        val correctAnswer = question.answer
        val messageResId: Int

        if(quizViewModel.isCheater){
            messageResId = R.string.judgment_toast
            question.userAnswer = false
        } else{
            if(userAnswer == correctAnswer){
                messageResId = R.string.correct_toast
                question.userAnswer = true
            } else{
                messageResId = R.string.incorrect_toast
                question.userAnswer = false
            }
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

        quizViewModel.isCheater = false
        changeResponseButtonState(true)
    }
}