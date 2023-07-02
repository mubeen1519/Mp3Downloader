package com.pawxy.mp3downloader.components.homescreen

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

sealed interface HomeScreenEvents{
    data class OnYoutubeLinkChange(val youtubeLink: String) : HomeScreenEvents
    data class OnDestinationFolderChange(val destinationFolder: Uri) : HomeScreenEvents
    data class OnVideoGrabInfo(val rootPath: String) : HomeScreenEvents
    object OnDownloadAnotherButtonClick : HomeScreenEvents
}