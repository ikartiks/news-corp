package newscorp.newscorpaustralia.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import io.reactivex.Single
import okhttp3.ResponseBody
import java.lang.reflect.Type


abstract class BaseService(protected var context: Context) {


    protected val endPoint: String
        get() = "https://firebasestorage.googleapis.com"

    val completeUrl: String
        get() {
            return url
        }


    protected abstract val parameters: Map<String, String>

    protected abstract val url: String

    protected abstract val acceptHeader: String?

    protected abstract val wrapperClass: Type

    protected abstract val additionalHeaders: Map<String, String>?

    protected abstract val httpMethod: HttpMethod?

    private// getting null pointer exception on some devices while trying to getActiveNetworkInfo();
    // happening from NetworkBroadcastReceiver
    val isConnectedToTheMobileNetwork: Boolean
        get() {
            try {
                val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val activeNetInfo = connectivityManager.activeNetworkInfo

                if (activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    return activeNetInfo.isConnected
                }
            } catch (ex: Exception) {
            }

            return false
        }
    protected var requestObject: Any? = null
    protected var postParams: Map<String, String>? = null

    protected fun createServices(): ServicesInterface<*> {
        return RestAdapterBuilder.restAdapterBuilder()
            .setClient()
            .setWrapperClass(wrapperClass)
            .setEndPoint(endPoint)
            .build()
    }

    protected enum class HttpMethod {
        GET, PUT, POST, DELETE
    }


    fun sendHttpRequestWithCacheStatus(): Single<ResponseWithCacheStatus> {
        checkArguments()

        var serviceResult: Single<ResponseBody>? = null
        val method = httpMethod
        val servicesInterface = createServices()

        if (method == HttpMethod.GET) {
            serviceResult = servicesInterface[completeUrl, parameters].cache()
        }

        return serviceResult!!.map { s ->
            val response = s.string()
            ResponseWithCacheStatus(response)
        }
    }

    private fun checkArguments() {
        val method =
            httpMethod ?: throw IllegalArgumentException("Http call method must not be empty.")

        require(!(completeUrl == null && completeUrl.isEmpty())) { "Service URL must be set." }

        require(
            !((HttpMethod.POST == method || HttpMethod.PUT == method) &&
                    requestObject == null &&
                    (postParams == null || postParams!!.size == 0))
        ) { "It seems there is something wrong with request, " + "there is no request object or params for POST/PUT." }
    }

    class ResponseWithCacheStatus constructor(val response: String) {

        init {
            Log.d("response %s", response)
        }
    }
}
