package ch.xlan.bookin.network

import ch.xlan.bookin.model.Book
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BookApi {
    @GET("/books")
    suspend fun getBooks(): List<Book>
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
