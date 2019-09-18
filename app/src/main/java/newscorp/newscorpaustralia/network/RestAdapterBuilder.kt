package newscorp.newscorpaustralia.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*

class RestAdapterBuilder {


    fun setClient(): RestAdapterBuilder {
        return this
    }

    fun setEndPoint(endPoint: String): RestAdapterBuilder {
        baseBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val b = OkHttpClient.Builder()
        b.addInterceptor(interceptor)
        val client = b.build()

        baseBuilder.client(client)

        baseBuilder.baseUrl(endPoint)
        return this
    }

    fun setWrapperClass(type: Type?): RestAdapterBuilder {
        // For no response services calls (code 204)
        if (type != null) {
            baseBuilder.addConverterFactory(GsonConverterFactory.create(getGsonConverter(type)))
        }
        return this
    }

    fun setConverter(gson: Gson?): RestAdapterBuilder {
        if (gson != null) {
            baseBuilder.addConverterFactory(GsonConverterFactory.create(gson))
        }
        return this
    }

    private fun getGsonConverter(wrapperType: Type): Gson {
        return GsonBuilder()
            //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            // Register an adapter to manage the date types as long values
            .registerTypeAdapter(
                Date::class.java,
                JsonDeserializer { json, typeOfT, context -> Date(json.asJsonPrimitive.asLong) })
            .create()
    }

    fun build(): ServicesInterface<*> {
        return baseBuilder.build().create(ServicesInterface::class.java)
    }

    private var baseBuilder: Retrofit.Builder = Retrofit.Builder()

    companion object {

        fun restAdapterBuilder(): RestAdapterBuilder {
            return RestAdapterBuilder()
        }
    }
}
