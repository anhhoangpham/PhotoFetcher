package com.anhpham.domain.usecase

import com.anhpham.domain.model.Photo
import com.anhpham.domain.repository.PhotoRepository

class FetchFlickrPhotosUseCase(private val flickrRepository: PhotoRepository): FetchPhotosUseCase {
    override suspend operator fun invoke(page: Int, perPage: Int): List<Photo> =
        flickrRepository.fetchPhotos(page, perPage)
}