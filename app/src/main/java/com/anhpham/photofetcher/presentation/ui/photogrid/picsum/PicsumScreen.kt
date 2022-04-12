package com.anhpham.photofetcher.presentation.ui.photogrid.picsum

import androidx.compose.runtime.Composable
import com.anhpham.domain.model.Photo
import com.anhpham.photofetcher.presentation.ui.photogrid.BaseAllPhotosScreen
import org.koin.androidx.compose.viewModel

@Composable
fun PicsumPhotosScreen(onPhotoSelected: (Photo) -> Unit) {
    val viewModel: PicsumViewModel by viewModel()
    BaseAllPhotosScreen(
        onPhotoSelected = onPhotoSelected,
        viewModel = viewModel
    )
}