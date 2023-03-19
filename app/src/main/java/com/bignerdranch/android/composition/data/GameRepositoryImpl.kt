package com.bignerdranch.android.composition.data

import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level
import com.bignerdranch.android.composition.domain.entity.Question
import com.bignerdranch.android.composition.domain.repository.GameRepository
import java.lang.Math.max
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )
            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )
            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                40
            )
            Level.HARD -> GameSettings(
                30,
                3,
                90,
                40
            )
        }
    }

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = kotlin.math.max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = kotlin.math.min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }
}