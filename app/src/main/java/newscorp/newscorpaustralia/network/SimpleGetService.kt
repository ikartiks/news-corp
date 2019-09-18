package newscorp.newscorpaustralia.network

import android.content.Context
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import newscorp.newscorpaustralia.model.Game
import java.lang.reflect.Type

class SimpleGetService(context: Context) : BaseService(context) {

    override val url: String
        get() = "v0/b/nca-dna-apps-dev.appspot.com/o/game.json"


    override val acceptHeader: String?
        get() = null

    override val wrapperClass: Type
        get() = Game::class.java

    override val parameters: Map<String, String>
        get() = mapOf(Pair("alt", "media"), Pair("token", "e36c1a14-25d9-4467-8383-a53f57ba6bfe"))

    override val additionalHeaders: Map<String, String>?
        get() = null

    override val httpMethod: HttpMethod?
        get() = HttpMethod.GET

    internal var disposable: Disposable? = null

    fun start(callback: ((game: Game) -> Unit)? = null) {
        disposable = sendHttpRequestWithCacheStatus()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { responseWithCacheStatus ->
                val gson = Gson()
                val game = gson.fromJson<Game>(
                    responseWithCacheStatus.response,
                    this@SimpleGetService.wrapperClass
                )
                callback?.invoke(game)
                disposable?.dispose()
            }
    }
}
