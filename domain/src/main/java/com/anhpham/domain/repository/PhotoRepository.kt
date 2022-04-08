package com.anhpham.domain.repository

import com.anhpham.domain.model.Photo

interface PhotoRepository {
    suspend fun fetchPhotos(page: Int, perPage: Int) : List<Photo>
}