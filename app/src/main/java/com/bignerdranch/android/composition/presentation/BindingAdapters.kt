package com.bignerdranch.android.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.domain.entity.GameResult

@BindingAdapter("required_answers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score_answers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("required_percentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count)
}
@BindingAdapter("score_percentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult){
    textView.text = String.format(
    textView.context.getString(R.string.score_percentage),
    getPercentOfRightAnswers(gameResult))
}
private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}
@BindingAdapter("emoji_result")
fun bindEmojiResult(emojiResult:ImageView,winner:Boolean){
    emojiResult.setImageResource(getSmileResId(winner))
}
private fun getSmileResId(winner:Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}
