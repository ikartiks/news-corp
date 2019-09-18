package newscorp.newscorpaustralia.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.StyleRes
import kotlinx.android.synthetic.main.dialog_loading.*
import newscorp.newscorpaustralia.R

class DialogLoader(
    context: Context, @StyleRes themeResId: Int,
    val textToShow: List<String>? = null,
    val isTransparent: Boolean
) : Dialog(context, themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (isTransparent)
            window?.setBackgroundDrawableResource(R.drawable.transparent_bg)
        else
            window?.setBackgroundDrawableResource(R.drawable.solid_bg)

        setContentView(R.layout.dialog_loading)

        textToShow?.let {
            if (it.isNotEmpty())
                title.text = it[0]
        }
        setCancelable(false)
    }
}