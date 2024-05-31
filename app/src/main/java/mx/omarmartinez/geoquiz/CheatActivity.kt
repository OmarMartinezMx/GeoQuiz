package mx.omarmartinez.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import mx.omarmartinez.geoquiz.databinding.ActivityCheatBinding
import mx.omarmartinez.geoquiz.viewmodel.CheatViewModel

private val EXTRA_ANSWER_SHOW = "mx.omarmartinez.geoquiz.answer_show"
private const val EXTRA_ANSWER_IS_TRUE = "mx.omarmartinez.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private val cheatViewModel: CheatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cheatViewModel.isAnswerShow = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        if(cheatViewModel.answerText != 0){
            binding.answerTextView.setText(cheatViewModel.answerText)
        }

        binding.showAnswerButton.setOnClickListener {
            cheatViewModel.answerText = when{
                cheatViewModel.isAnswerShow -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(cheatViewModel.answerText)
            setAnswerShownResult()
        }
    }

    private fun setAnswerShownResult(){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOW, true)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}