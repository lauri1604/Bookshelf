package com.example.bookshelf.network
import com.example.bookshelf.model.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Paso 3: describir la petición HTTP a Open Library y los parámetros de búsqueda.
interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String = "jazz history",
        @Query("fields") fields: String =
            "key,title,author_name,first_publish_year,isbn,cover_i,edition_count",
        @Query("limit") limit: Int = 20
    ): BookSearchResponse
}
