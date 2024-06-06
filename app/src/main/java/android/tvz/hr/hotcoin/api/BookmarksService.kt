package android.tvz.hr.hotcoin.api

import android.tvz.hr.hotcoin.model.Article
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarksService {

    @GET("/api/articles")
    fun getAllBookmarkedArticles(): Call<List<Article>>

    @POST("/api/articles")
    fun createArticle(@Body article: Article): Call<Article>

    @DELETE("/api/articles/{title}")
    fun deleteArticle(@Path("title") title:String): Call<Void>
}