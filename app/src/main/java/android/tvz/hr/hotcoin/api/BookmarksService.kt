package android.tvz.hr.hotcoin.api

import android.tvz.hr.hotcoin.model.Article
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarksService {

    @GET("/api/articles/{userId}")
    fun getAllBookmarkedArticles(@Path("userId") userId: String): Call<List<Article>>

    @GET("/api/articles/latest")
    fun getLatestArticle(): Call<Article>

    @POST("/api/articles/{userId}")
    fun createArticle(@Body article: Article, @Path("userId") userId: String): Call<Article>

    @DELETE("/api/articles/{userId}/{title}")
    fun deleteArticle(@Path("title") title:String, @Path("userId") userId: String): Call<Void>
}