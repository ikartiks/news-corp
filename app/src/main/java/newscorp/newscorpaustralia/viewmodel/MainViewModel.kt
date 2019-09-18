package newscorp.newscorpaustralia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import newscorp.newscorpaustralia.model.Game
import newscorp.newscorpaustralia.model.Items


class MainViewModel(applicationX: Application) : AndroidViewModel(applicationX) {

    private var currentQuestion = -1
    var totalQuestions: Int = 0
    var userScore = 0
    var questionsArray: ArrayList<Items> = ArrayList()

    fun initialize(game: Game) {
        questionsArray = game.items
        totalQuestions = game.items.size
    }

    fun getNextQuestion(): Items? {
        currentQuestion++
        if (currentQuestion <= totalQuestions)
            return questionsArray[currentQuestion]
        return null
    }

    fun getCurrentQuestion(): Items? {
        if (currentQuestion == -1)
            currentQuestion++
        if (currentQuestion <= totalQuestions)
            return questionsArray[currentQuestion]
        return null
    }

    fun incrementUserScore() {
        userScore += 2
    }

    fun decrementUserScore() {
        userScore--
    }
}

