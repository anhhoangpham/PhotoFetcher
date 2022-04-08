package com.anhpham.photofetcher.framework.flickr

import com.anhpham.photofetcher.presentation.PhotoApplication
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.flickr.com/services/rest/"
private const val CONNECTION_TIMEOUT_MS: Long = 10

object FlickrApiClient {
    val client: FlickrApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .client(
                OkHttpClient.Builder().connectTimeout(
                    CONNECTION_TIMEOUT_MS,
                    TimeUnit.SECONDS
                ).addInterceptor(HttpLoggingInterceptor {
                    Platform.get().log(it)
                    PhotoApplication.instance.getLogWriter().logRetrofit(it)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .build()
            .create(FlickrApiService::class.java)
    }
}