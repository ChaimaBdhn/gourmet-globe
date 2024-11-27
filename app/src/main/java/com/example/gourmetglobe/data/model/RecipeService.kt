package com.example.gourmetglobe.data.model
import com.example.gourmetglobe.data.api.RecipeApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RecipeService {
    private const val BASE_URL = "https://api.spoonacular.com/"

    val api: RecipeApi by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level= HttpLoggingInterceptor.Level.BODY
            }).build())
            .build()
            .create(RecipeApi::class.java)
    }
}
