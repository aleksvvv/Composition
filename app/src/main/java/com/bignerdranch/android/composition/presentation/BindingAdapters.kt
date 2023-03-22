package com.bignerdranch.android.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.domain.entity.GameResult

interface OnOptionClick {
    fun onClick(opt: Int)
}
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
@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, state:Boolean){
    textView.setTextColor(getColorByState(textView.context,state))
}
@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar,state: Boolean){
    val color = getColorByState(progressBar.context,state)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}
@BindingAdapter("numberToString")
fun bindNumberToString(textView: TextView, number: Int){
    textView.text = number.toString()
}
@BindingAdapter("onOptionClickList")
fun bindOnOptionClick(textView: TextView, option: OnOptionClick){
    textView.setOnClickListener {
        option.onClick(textView.text.toString().toInt())
    }
}