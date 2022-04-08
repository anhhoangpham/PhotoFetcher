package com.anhpham.photofetcher.presentation.ui.photo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.anhpham.domain.model.Photo

@Composable
fun PhotoScreen(photo: Photo) {
    Scaffold {
        ConstraintLayout {
            val (image, text) = createRefs()

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        modifier = Modifier.padding(10.dp)
                    )
                },
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
            photo.title?.let { title ->
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .constrainAs(text) {
                            bottom.linkTo(image.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = title,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}