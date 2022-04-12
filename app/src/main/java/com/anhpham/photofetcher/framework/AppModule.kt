package com.anhpham.photofetcher.framework

import com.anhpham.data.datasource.PhotosDataSource
import com.anhpham.data.repository.PhotosRepositoryImpl
import com.anhpham.domain.repository.PhotoRepository
import com.anhpham.domain.usecase.FetchFlickrPhotosUseCase
import com.anhpham.domain.usecase.FetchPicsumPhotosUseCase
import com.anhpham.photofetcher.framework.flickr.FlickrDataSource
import com.anhpham.photofetcher.framework.picsum.PicsumDataSource
import com.anhpham.photofetcher.presentation.ui.photogrid.flickr.FlickrViewModel
import com.anhpham.photofetcher.presentation.ui.photogrid.picsum.PicsumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val FLICKR = "flickr"
const val PICSUM = "picsum"

val appModule = module {
    single<PhotosDataSource>(named(FLICKR)) { FlickrDataSource() }
    single<PhotoRepository>(named(FLICKR)) { PhotosRepositoryImpl(get(named(FLICKR))) }
    single { FetchFlickrPhotosUseCase(get(named(FLICKR))) }
    viewModel { FlickrViewModel(get()) }

    single<PhotosDataSource>(named(PICSUM)) { PicsumDataSource() }
    single<PhotoRepository>(named(PICSUM)) { PhotosRepositoryImpl(get(named(PICSUM))) }
    single { FetchPicsumPhotosUseCase(get(named(PICSUM))) }
    viewModel { PicsumViewModel(get()) }
}