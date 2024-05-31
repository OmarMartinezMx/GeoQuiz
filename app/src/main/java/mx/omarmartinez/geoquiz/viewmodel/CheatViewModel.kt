package mx.omarmartinez.geoquiz.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val IS_ANSWER_SHOW_KEY = "IS_CHEATER_KEY"
const val ANSWER_TEXT = "ANSWER_TEXT"

class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel(){
    var isAnswerShow: Boolean
        get() = savedStateHandle[IS_ANSWER_SHOW_KEY] ?: false
        set(value) = savedStateHandle.set(IS_ANSWER_SHOW_KEY, value)

    var answerText: Int
        get() = savedStateHandle[ANSWER_TEXT] ?: 0
        set(value) = savedStateHandle.set(ANSWER_TEXT, value)
}