package com.anhpham.photofetcher.framework.flickr

import com.anhpham.data.datasource.PhotosDataSource
import com.anhpham.domain.model.Photo
import kotlinx.coroutines.coroutineScope

class FlickrDataSource : PhotosDataSource {
    override suspend fun fetchPhotos(page: Int, perPage: Int): List<Photo> {
        val response = FlickrApiClient.client.fetchImages(page = page, perPage = perPage)
        response.photos.photo?.let { photos ->
            return photos.map {
                val urlFormat = "https://farm%d.staticflickr.com/%s/%s_%s_%s.jpg"
                Photo(
                    id = it.id,
                    title = it.title,
                    url = String.format(urlFormat, it.farm, it.server, it.id, it.secret, "b"),
                    thumbnailUrl = String.format(urlFormat, it.farm, it.server, it.id, it.secret, "q")
                )
            }
        }
        return ArrayList()
    }
}