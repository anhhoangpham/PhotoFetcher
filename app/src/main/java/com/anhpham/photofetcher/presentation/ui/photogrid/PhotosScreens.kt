package com.anhpham.photofetcher.presentation.ui.photogrid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.anhpham.data.repository.PhotosRepositoryImpl
import com.anhpham.domain.model.Photo
import com.anhpham.photofetcher.framework.flickr.FlickrDataSource
import com.anhpham.photofetcher.framework.picsum.PicsumDataSource

@Composable
fun FlickrPhotosScreen(onPhotoSelected: (Photo) -> Unit) {
    BaseAllPhotosScreen(
        onPhotoSelected = onPhotoSelected,
        viewModel = PhotosViewModel(PhotosRepositoryImpl(FlickrDataSource()))
    )
}

@Composable
fun PicsumPhotosScreen(onPhotoSelected: (Photo) -> Unit) {
    BaseAllPhotosScreen(
        onPhotoSelected = onPhotoSelected,
        viewModel = PhotosViewModel(PhotosRepositoryImpl(PicsumDataSource()))
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun BaseAllPhotosScreen(
    onPhotoSelected: (Photo) -> Unit,
    viewModel: PhotosViewModel
) {
    val photos = viewModel.photos.observeAsState()
    viewModel.fetchPhotos()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(125.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        content = {
            photos.value?.let {
                items(it.size) { index ->
                    Card(
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 8.dp,
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .height(125.dp),
                        onClick = { onPhotoSelected(it[index]) }
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it[index].thumbnailUrl)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = it[index].title
                        )
                    }
                }
            }
        }
    )
}