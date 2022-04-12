package com.anhpham.photofetcher.presentation.ui.photogrid.flickr

import com.anhpham.domain.usecase.FetchFlickrPhotosUseCase
import com.anhpham.photofetcher.presentation.ui.photogrid.BasePhotosViewModel

class FlickrViewModel(fetchFlickrPhotos: FetchFlickrPhotosUseCase) : BasePhotosViewModel(fetchFlickrPhotos)