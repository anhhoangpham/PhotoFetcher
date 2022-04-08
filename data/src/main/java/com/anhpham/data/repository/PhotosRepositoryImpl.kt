package com.anhpham.data.repository

import com.anhpham.data.datasource.PhotosDataSource
import com.anhpham.domain.model.Photo
import com.anhpham.domain.repository.PhotoRepository

class PhotosRepositoryImpl(
    private val photosDataSource: PhotosDataSource
) : PhotoRepository {

    override suspend fun fetchPhotos(page: Int, perPage: Int): List<Photo> =
        photosDataSource.fetchPhotos(page, perPage)
}