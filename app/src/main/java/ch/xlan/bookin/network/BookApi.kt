package ch.xlan.bookin.network

import ch.xlan.bookin.model.Book
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface BookApi {
    @GET("/books")
    suspend fun getBooks(): List<Book>

    @Streaming
    @GET
    suspend fun downloadBook(@Url url: String): ResponseBody
}

object ApiClient {
    private const val BASE_URL = "https://books.etml.net"
    private const val AUTH_TOKEN = "oat_MQ.dURyWFZGNjNKZEFyY3NfNmIyYTZKRkZUTExHWWtnRkhTd3NpUUxhcDE4NTY2OTM5NTA"

    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val instance: BookApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }
}
