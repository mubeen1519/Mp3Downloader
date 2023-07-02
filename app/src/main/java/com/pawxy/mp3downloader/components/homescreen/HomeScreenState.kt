package com.pawxy.mp3downloader.components.homescreen

import android.net.Uri
import com.pawxy.mp3downloader.model.data.VideoInfo

data class HomeScreenState(
    val youtubeLink: String = "",
    val destinationFolder: Uri = Uri.EMPTY,

    //initial screen content
    val step: HomeScreenStep = HomeScreenStep.INITIAL,

    val toastErrorMessage: String? = null,
    // Used to display message in ErrorScreen
    val errorMessage: String = "",

    val videoInfo: VideoInfo? = null,
    val downloadPercentComplete: Float = 0f,
)

//enum class use for different steps to download and move onto screen
enum class HomeScreenStep {
    INITIAL,
    GRABBING,
    DOWNLOADING,
    CONVERTING,
    SAVING,
    SUCCESS,
    FAILED
}