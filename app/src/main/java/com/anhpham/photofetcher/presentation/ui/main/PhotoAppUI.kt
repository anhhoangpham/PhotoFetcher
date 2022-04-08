package com.anhpham.photofetcher.presentation.ui.main

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anhpham.domain.model.Photo
import com.anhpham.photofetcher.presentation.PhotoApplication
import com.anhpham.photofetcher.presentation.ui.photo.PhotoParamType
import com.anhpham.photofetcher.presentation.ui.photo.PhotoScreen
import com.anhpham.photofetcher.presentation.ui.photogrid.FlickrPhotosScreen
import com.anhpham.photofetcher.presentation.ui.photogrid.PicsumPhotosScreen
import com.google.gson.Gson


@Composable
fun PhotoApp() {
    val navController = rememberNavController()
    val items = listOf(
        AppScreens.FlickrPhotos,
        AppScreens.PicsumPhotos,
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, items) },
        topBar = { TopAppBarWithMenu() }
    ) { innerPadding ->
        MainContent(navController, innerPadding)
    }
}

@Composable
private fun MainContent(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = AppScreens.FlickrPhotos.route,
        Modifier.padding(innerPadding)
    ) {
        composable(AppScreens.FlickrPhotos.route) {
            FlickrPhotosScreen { onPhotoSelected(it, navController) }
        }
        composable(AppScreens.PicsumPhotos.route) {
            PicsumPhotosScreen { onPhotoSelected(it, navController) }
        }
        composable(AppScreens.PhotoDetail.route + "/{photo}",
            arguments = listOf(
                navArgument("photo") {
                    type = PhotoParamType()
                }
            )) {
            val photoData = it.arguments?.getParcelable<Photo>("photo")
            log("Displaying photo: $photoData")
            photoData?.let { PhotoScreen(photoData) }
        }
    }
}

private fun onPhotoSelected(it: Photo, navController: NavHostController) {
    val json = Uri.encode(Gson().toJson(it))
    navController.navigate(AppScreens.PhotoDetail.route + "/$json")
}

@Preview
@Composable
private fun Preview() {
    PhotoApp()
}

private fun log(message: String) {
    PhotoApplication.instance.getLogWriter().logInfoMessage(message)
    Log.d("Test", message)
}