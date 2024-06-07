package android.tvz.hr.hotcoin

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.tvz.hr.hotcoin.api.BookmarksService
import android.tvz.hr.hotcoin.api.NewsService
import android.tvz.hr.hotcoin.model.Article
import android.tvz.hr.hotcoin.util.Constants
import android.tvz.hr.hotcoin.util.RetrofitHelper
import android.widget.RemoteViews
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsWidget : AppWidgetProvider() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for(appWidgetId in appWidgetIds){
                GlobalScope.launch(Dispatchers.IO){
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }


    internal suspend fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ){
        val bookmarksService = RetrofitHelper().createService(BookmarksService::class.java,Constants.DATABASE_URL)

        bookmarksService.getLatestArticle().enqueue(object : Callback<Article> {
            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                if(response.isSuccessful){
                    val article = response.body()
                    val views = RemoteViews(context.packageName,R.layout.news_widget)

                    if(article != null){
                        GlobalScope.launch(Dispatchers.IO){
                            val bitmap = withContext(Dispatchers.IO){
                                val loader = ImageLoader(context)
                                val request = ImageRequest.Builder(context)
                                    .data(article.urlToImage)
                                    .allowHardware(false)
                                    .build()
                                val result = (loader.execute(request) as SuccessResult).drawable
                                (result as BitmapDrawable).bitmap
                            }

                            withContext(Dispatchers.Main){
                                views.setTextViewText(R.id.news_widget_title, article.title)
                                views.setImageViewBitmap(R.id.news_widget_image,bitmap)
                                appWidgetManager.updateAppWidget(appWidgetId,views)
                            }
                        }
                    }

                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {

            }
        })
    }
}