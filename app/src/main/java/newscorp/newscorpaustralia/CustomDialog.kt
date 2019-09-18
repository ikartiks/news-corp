package newscorp.newscorpaustralia

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.success_failure_pop.*
import newscorp.newscorpaustralia.model.Items

class CustomDialog(
    context: Context,
    cancelable: Boolean,
    cancelListener: DialogInterface.OnCancelListener?
) :
    Dialog(context, cancelable, cancelListener), View.OnClickListener {

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.close -> {
                cancel()
                dismiss()
            }

        }
    }

    var isSuccess = false
    var item: Items? = null

    constructor(
        context: Context,
        flag: Boolean,
        cancelListener: DialogInterface.OnCancelListener?,
        item: Items?
    ) : this(context, true, cancelListener) {
        isSuccess = flag
        this.item = item
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_failure_pop)

        this.window?.attributes?.windowAnimations = R.style.DialogTheme
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        close.setOnClickListener(this)

        if (!isSuccess) {

            point.text = context.getString(R.string.minus)
            that.text = context.getString(R.string.incorrect)

            item?.headlines?.let {
                name.text =
                    context.getString(R.string.correct_answer, it.get(item?.correctAnswerIndex!!))
            }

            forYou.text = context.getString(R.string.incorrect_for_you)
            successContainer.setBackgroundResource(R.drawable.round_bg_failure)
        } else {
            name.visibility = View.INVISIBLE
        }
    }


}