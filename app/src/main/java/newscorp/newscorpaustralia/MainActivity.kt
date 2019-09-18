package newscorp.newscorpaustralia

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import newscorp.newscorpaustralia.base.ActivityBase
import newscorp.newscorpaustralia.network.SimpleGetService
import newscorp.newscorpaustralia.viewmodel.MainViewModel
import newscorp.newscorpaustralia.viewmodel.ViewModelFactory


class MainActivity : ActivityBase(), View.OnClickListener, DialogInterface.OnCancelListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var simpleGetService: SimpleGetService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val factory = ViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        simpleGetService = SimpleGetService(this)

        if (viewModel.questionsArray.isEmpty()) {
            if (isConnected()) {
                showLoadingDialog(listOf(getString(R.string.loading)), true)
                simpleGetService.start {
                    viewModel.initialize(it)
                    loadCurrentQuestion()
                }
            } else {
                showCustomMessage(getString(R.string.connect_to_net))
            }
        } else {
            loadCurrentQuestion()
        }

        optionThree.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionOne.setOnClickListener(this)
        skip.setOnClickListener(this)
        readArticle.setOnClickListener(this)
    }

    private fun loadCurrentQuestion() {

        score.text = getString(
            R.string.questions_answered_correctly,
            viewModel.userScore,
            viewModel.totalQuestions * 2
        )
        val question = viewModel.getCurrentQuestion()

        if (question == null)
            showCustomMessage(getString(R.string.over))

        question?.let {

            val glide = Glide.with(image.context.applicationContext)
            val placeHolder = R.drawable.ic_launcher_background
            val requestOption = RequestOptions().placeholder(placeHolder)
            glide.load(it.imageUrl).apply(requestOption).into(image)

            standfirst.text = question.standFirst
            optionOne.text = question.headlines[0]
            optionTwo.text = question.headlines[1]
            optionThree.text = question.headlines[2]
        }
        hideLoadingDialog()
    }

    private fun loadNextQuestion() {

        score.text = getString(
            R.string.score_questions_answered_correctly,
            viewModel.userScore,
            viewModel.totalQuestions * 2
        )
        val question = viewModel.getNextQuestion()

        if (question == null)
            showCustomMessage(getString(R.string.over))

        question?.let {

            val glide = Glide.with(image.context.applicationContext)
            val placeHolder = R.drawable.ic_launcher_background
            val requestOption = RequestOptions().placeholder(placeHolder)
            glide.load(it.imageUrl).apply(requestOption).into(image)

            standfirst.text = question.standFirst
            optionOne.text = question.headlines.get(0)
            optionTwo.text = question.headlines.get(1)
            optionThree.text = question.headlines.get(2)
        }
    }


    override fun onClick(view: View?) {

        val currentQuestion = viewModel.getCurrentQuestion()
        val dialog: Dialog?
        when (view?.id) {
            R.id.optionOne -> {

                if (currentQuestion?.correctAnswerIndex == 0) {
                    viewModel.incrementUserScore()
                    dialog = CustomDialog(this, true, this, currentQuestion)
                } else {
                    viewModel.decrementUserScore()
                    dialog = CustomDialog(this, false, this, currentQuestion)

                }

                dialog.show()
            }
            R.id.optionTwo -> {

                if (currentQuestion?.correctAnswerIndex == 1) {
                    dialog = CustomDialog(this, true, this, currentQuestion)
                    viewModel.incrementUserScore()
                } else {
                    dialog = CustomDialog(this, false, this, currentQuestion)
                    viewModel.decrementUserScore()
                }
                dialog.show()

            }
            R.id.optionThree -> {
                if (currentQuestion?.correctAnswerIndex == 2) {
                    dialog = CustomDialog(this, true, this, currentQuestion)
                    viewModel.incrementUserScore()
                } else {
                    dialog = CustomDialog(this, false, this, currentQuestion)
                    viewModel.decrementUserScore()
                }
                dialog.show()
            }
            R.id.readArticle -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentQuestion?.storyUrl))
                if (resolveActivity(browserIntent))
                    startActivity(browserIntent)
                else
                    showCustomMessage(getString(R.string.browser))
            }
            R.id.skip -> {
                loadNextQuestion()
            }
        }
    }

    override fun onCancel(p0: DialogInterface?) {
        loadNextQuestion()
    }

}
