package ikartiks.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import newscorp.newscorpaustralia.R
import newscorp.newscorpaustralia.model.Game
import newscorp.newscorpaustralia.model.Items


class MainViewModel (val applicationX: Application) : AndroidViewModel(applicationX) {

    var currentQuestion = -1
    var totalQuestions:Int = 0
    var userScore = 0
    var  questionsArray:ArrayList<Items>

    init {
        val  gson =  Gson()
        val reader =  applicationX.resources.openRawResource(R.raw.codebeautify)
        val inputAsString = reader.bufferedReader().use { it.readText() }
        val data = gson.fromJson(inputAsString, Game::class.java)
        questionsArray = data.items
        totalQuestions = questionsArray.size
    }

    fun getNextQuestion():Items?{

        currentQuestion++
        if(currentQuestion<=totalQuestions)
            return questionsArray.get(currentQuestion)
        return null
    }

    fun getCurrentQuestion():Items?{

        if(currentQuestion==-1)
            currentQuestion++
        if(currentQuestion<=totalQuestions)
            return questionsArray.get(currentQuestion)
        return null
    }

    fun incrementUserScore(){
        userScore++
    }
    fun decrementUserScore(){
        userScore--
    }
}

