package com.bignerdranch.android.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.data.GameRepositoryImpl
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level
import com.bignerdranch.android.composition.domain.entity.Question
import com.bignerdranch.android.composition.domain.usecases.GenerateQuestionsUseCase
import com.bignerdranch.android.composition.domain.usecases.GetGameSettingsUseCase
import kotlin.concurrent.timer

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepositoryImpl
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionsUseCase = GenerateQuestionsUseCase(repository)
    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private var timer:CountDownTimer? = null
    private val context = application


    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime:LiveData<String>
    get() = _formattedTime
    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult
    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
       getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }
    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }
    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun startTimer(){
        val timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds* MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS){
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = timeFormatted(millisUntilFinished)
            }
            override fun onFinish() {
                finishGame()
            }
        }
            timer.start()
    }
    private fun timeFormatted(millis: Long):String{
        val second = millis / MILLIS_IN_SECONDS
        val minut = second / SECONDS
        val secondLast = second - (minut * SECONDS)
        return String.format("%02d : %02d",minut,secondLast)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
    private fun finishGame(){
       _gameResult.value = GameResult(
            winner = enoughPercent.value == true && enoughCount.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfQuestions,
            gameSettings = gameSettings
        )

    }
    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }
    private fun generateQuestion(){
        val maxSumValue = gameSettings.maxSumValue
        _question.value = generateQuestionsUseCase(maxSumValue)
    }
    companion object{
        const val MILLIS_IN_SECONDS = 1000L
        const val SECONDS = 60
    }
}