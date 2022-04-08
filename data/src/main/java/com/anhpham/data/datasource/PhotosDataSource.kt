package com.anhpham.data.datasource

import com.anhpham.domain.model.Photo

interface PhotosDataSource {
    suspend fun fetchPhotos(page: Int, perPage: Int): List<Photo>
}