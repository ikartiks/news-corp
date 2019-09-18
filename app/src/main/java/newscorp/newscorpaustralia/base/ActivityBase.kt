package newscorp.newscorpaustralia.base

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import newscorp.newscorpaustralia.R

abstract class ActivityBase : AppCompatActivity() {


    fun isConnected(): Boolean {

        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }

    fun showCustomMessage(message: String) {

        val adb = AlertDialog.Builder(this)
        adb.setTitle(this.resources.getString(R.string.app_name))
        adb.setMessage(message)
        adb.setPositiveButton("OK", { dialog, which -> dialog.dismiss() })
        adb.create().show()
    }

    fun resolveActivity(intent: Intent): Boolean {

        val pm = this.packageManager
        val info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return info != null
    }

    private var loadingDialogLoader: DialogLoader? = null
    fun showLoadingDialog(messageList: List<String>? = null, isTransparent: Boolean = false) {
        hideLoadingDialog()
        loadingDialogLoader = DialogLoader(
            this,
            R.style.PartialScreenLoadingDialog,
            textToShow = messageList,
            isTransparent = isTransparent
        )
        loadingDialogLoader?.show()
    }

    fun hideLoadingDialog() {
        loadingDialogLoader?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }
}