import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientFace {
    private const val BASE_URL = "https://mlapi-dot-bangkit-tes.et.r.appspot.com/"

    private val client = OkHttpClient.Builder().build()

    val instance: UserApiFace by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserApiFace::class.java)
    }
}
