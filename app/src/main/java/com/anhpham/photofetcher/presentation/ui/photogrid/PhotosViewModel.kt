package com.anhpham.photofetcher.presentation.ui.photogrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anhpham.domain.model.Photo
import com.anhpham.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosViewModel(private val repo: PhotoRepository) : ViewModel() {
    private val _photoList = MutableLiveData<ArrayList<Photo>>()
    val photos: LiveData<ArrayList<Photo>> = _photoList

    private var tempPhotos = ArrayList<Photo>()
    private var currentPage = 0

    fun fetchPhotos() {
        viewModelScope.launch {
            currentPage = 0
            tempPhotos.clear()
            requestPhotos()
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            requestPhotos()
        }
    }

    private suspend fun requestPhotos() {
        withContext(Dispatchers.IO) {
            currentPage++
            val photos = repo.fetchPhotos(page = currentPage, perPage = ITEMS_PER_PAGE)
            tempPhotos.addAll(photos)
            _photoList.postValue(tempPhotos)
        }
    }

    companion object {
        const val ITEMS_PER_PAGE = 50
    }
}