package com.anhpham.photofetcher.framework.picsum

import android.net.Uri
import com.anhpham.data.datasource.PhotosDataSource
import com.anhpham.domain.model.Photo

class PicsumDataSource : PhotosDataSource {
    override suspend fun fetchPhotos(page: Int, perPage: Int): List<Photo> {
        val response = PicsumApiClient.client.listImages(page = page, limit = perPage)
        return response.map {
            Photo(
                id = it.id.toString(),
                title = Uri.parse(it.url).lastPathSegment,
                url = it.downloadUrl,
                thumbnailUrl = "https://picsum.photos/id/${it.id}/150/150"
            )
        }
    }
}