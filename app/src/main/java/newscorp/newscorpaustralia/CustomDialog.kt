package newscorp.newscorpaustralia

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.success_failure_pop.*
import newscorp.newscorpaustralia.model.Items

class CustomDialog(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) :
    Dialog(context, cancelable, cancelListener), View.OnClickListener {

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.close->{
                cancel()
                dismiss()
            }

        }
    }

    var isSuccess= false
    var item:Items?=null

    constructor(context: Context,flag:Boolean,cancelListener: DialogInterface.OnCancelListener?, item:Items?):this(context,true,cancelListener){
        isSuccess= flag
        this.item=item
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_failure_pop)

        this.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme

        this.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        close.setOnClickListener(this)

        if(!isSuccess){

            point.text=context.getString(R.string.minus)
            that.text=context.getString(R.string.incorrect)
            close.setTextColor(context.resources.getColor(R.color.red))
            name.text=item?.headlines?.get(item?.correctAnswerIndex!!)
            //successContainer.background()
            successContainer.setBackgroundResource(R.drawable.round_bg_failure)
        }
    }


}