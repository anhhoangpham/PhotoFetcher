package com.anhpham.domain.usecase

import com.anhpham.domain.model.Photo
import com.anhpham.domain.repository.PhotoRepository

interface FetchPhotosUseCase {
    suspend operator fun invoke(page: Int, perPage: Int): List<Photo>
}