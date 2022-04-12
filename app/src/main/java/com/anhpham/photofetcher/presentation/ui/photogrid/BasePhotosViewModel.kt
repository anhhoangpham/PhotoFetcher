package com.anhpham.photofetcher.presentation.ui.photogrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anhpham.domain.model.Photo
import com.anhpham.domain.usecase.FetchPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BasePhotosViewModel(private val fetchPhoto: FetchPhotosUseCase) : ViewModel() {
    private val _photoList = MutableLiveData<ArrayList<Photo>>()
    val photos: LiveData<ArrayList<Photo>> = _photoList

    private var tempPhotos = ArrayList<Photo>()
    private var currentPage = 0

    init {
        fetchPhotos()
    }

    fun fetchPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            currentPage = 0
            tempPhotos.clear()
            requestPhotos()
        }
    }

    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            requestPhotos()
        }
    }

    private suspend fun requestPhotos() {
        currentPage++
        val photos = fetchPhoto(page = currentPage, perPage = ITEMS_PER_PAGE)
        tempPhotos.addAll(photos)

        withContext(Dispatchers.Main) {
            _photoList.postValue(tempPhotos)
        }
    }

    companion object {
        const val ITEMS_PER_PAGE = 50
    }
}