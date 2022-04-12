package com.anhpham.photofetcher.presentation.ui.photogrid.flickr

import androidx.compose.runtime.Composable
import com.anhpham.domain.model.Photo
import com.anhpham.photofetcher.presentation.ui.photogrid.BaseAllPhotosScreen
import org.koin.androidx.compose.viewModel

@Composable
fun FlickrPhotosScreen(onPhotoSelected: (Photo) -> Unit) {
    val viewModel: FlickrViewModel by viewModel()
    BaseAllPhotosScreen(
        onPhotoSelected = onPhotoSelected,
        viewModel = viewModel
    )
}