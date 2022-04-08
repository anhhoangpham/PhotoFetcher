package com.anhpham.photofetcher.framework.picsum

import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApiService {
    @GET("v2/list")
    suspend fun listImages(
        @Query(value = "page") page: Int,
        @Query(value = "limit") limit: Int
    ): List<PicsumPhotoResponse>
}