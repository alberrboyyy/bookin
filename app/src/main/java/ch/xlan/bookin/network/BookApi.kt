package ch.xlan.bookin.network

import ch.xlan.bookin.model.Book
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface BookApi {
    @GET("/books")
    suspend fun getBooks(): List<Book>

    @Streaming
    @GET("/book/{id}/file")
    suspend fun downloadBook(@Path("id") id: Int): ResponseBody
}

object ApiClient {
    private const val BASE_URL = "http://10.0.200.3:6732"

    val instance: BookApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }
}
