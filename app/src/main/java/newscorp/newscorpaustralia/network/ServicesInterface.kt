package newscorp.newscorpaustralia.network


import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ServicesInterface<T> {

    @GET("/{url}")
    operator fun get(
        @Path(value = "url", encoded = true) url: String,
        @QueryMap(encoded = true) params: Map<String, String>
    ): Single<ResponseBody>
}
