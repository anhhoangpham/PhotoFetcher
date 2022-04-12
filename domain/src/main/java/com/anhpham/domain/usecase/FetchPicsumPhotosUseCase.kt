package com.anhpham.domain.usecase

import com.anhpham.domain.model.Photo
import com.anhpham.domain.repository.PhotoRepository

class FetchPicsumPhotosUseCase(private val picsumRepository: PhotoRepository): FetchPhotosUseCase {
    override suspend operator fun invoke(page: Int, perPage: Int): List<Photo> =
        picsumRepository.fetchPhotos(page, perPage)
}