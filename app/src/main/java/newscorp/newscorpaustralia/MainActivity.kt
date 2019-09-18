package newscorp.newscorpaustralia

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ikartiks.expensetracker.viewmodel.MainViewModel
import ikartiks.expensetracker.viewmodel.ViewModelFactory


class MainActivity : ActivityBase(), View.OnClickListener,DialogInterface.OnCancelListener {

    private lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val factory = ViewModelFactory(application)
        //val addViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // note we are calling get method on factory and not onCreate, so it will decide if
        // it wants to reuse old instance or create new using create method in our factory
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)


        //score.text = "$userScore of $totalQuestions"
        loadCurrentQuestion()
        //loadNextQuestion()

        optionThree.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionOne.setOnClickListener(this)
        skip.setOnClickListener(this)
        readArticle.setOnClickListener(this)

        //showCustomMessage(data.product!!)
    }

    fun loadCurrentQuestion(){

        score.text = "${viewModel.userScore} of ${viewModel.totalQuestions}"
        val question = viewModel.getCurrentQuestion()

        if(question==null)
            showCustomMessage(getString(R.string.over))

        question?.let {

            val glide = Glide.with(image.context.applicationContext)
            val placeHolder = R.drawable.ic_launcher_background
            val requestOption = RequestOptions().placeholder(placeHolder)
            glide.load(it.imageUrl).apply(requestOption).into(image)

            standfirst.text= question.standFirst
            optionOne.text = question.headlines.get(0)
            optionTwo.text = question.headlines.get(1)
            optionThree.text = question.headlines.get(2)
        }
    }

    fun loadNextQuestion(){

        score.text = "Score ${viewModel.userScore} of ${viewModel.totalQuestions}"
        val question = viewModel.getNextQuestion()

        if(question==null)
            showCustomMessage(getString(R.string.over))

        question?.let {

            val glide = Glide.with(image.context.applicationContext)
            val placeHolder = R.drawable.ic_launcher_background
            val requestOption = RequestOptions().placeholder(placeHolder)
            glide.load(it.imageUrl).apply(requestOption).into(image)

            standfirst.text= question.standFirst
            optionOne.text = question.headlines.get(0)
            optionTwo.text = question.headlines.get(1)
            optionThree.text = question.headlines.get(2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view: View?) {

        val currentQuestion = viewModel.getCurrentQuestion()
        var dialog:Dialog? =null
        when(view?.id){
            R.id.optionOne ->{

                if(currentQuestion?.correctAnswerIndex==0){
                    viewModel.incrementUserScore()
                    dialog= CustomDialog(this,true,this,currentQuestion)
                }else{
                    viewModel.decrementUserScore()
                    dialog= CustomDialog(this,false,this,currentQuestion)

                }

                dialog?.show()
            }
            R.id.optionTwo ->{

                if(currentQuestion?.correctAnswerIndex==1){
                    dialog= CustomDialog(this,true,this,currentQuestion)
                    viewModel.incrementUserScore()
                }else{
                    dialog= CustomDialog(this,false,this,currentQuestion)
                    viewModel.decrementUserScore()
                }
                dialog?.show()

            }
            R.id.optionThree ->{
                if(currentQuestion?.correctAnswerIndex==2){
                    dialog= CustomDialog(this,true,this,currentQuestion)
                    viewModel.incrementUserScore()
                }else{
                    dialog= CustomDialog(this,false,this,currentQuestion)
                    viewModel.decrementUserScore()
                }
                dialog?.show()
            }
            R.id.readArticle ->{

                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentQuestion?.storyUrl))
                if(resolveActivity(browserIntent))
                    startActivity(browserIntent)
                else
                    showCustomMessage(getString(R.string.browser))
            }
            R.id.skip ->{
                loadNextQuestion()
            }
        }
    }

    override fun onCancel(p0: DialogInterface?) {
        loadNextQuestion()
    }

}
