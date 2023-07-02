package com.pawxy.mp3downloader.model.data

import kotlinx.coroutines.flow.Flow

interface StepsRepository {
    suspend fun getInfo(url: String): Flow<Response<VideoInfo>>
    suspend fun downloadAudio(url: String): Flow<Response<Unit>>
    fun callBackFromMain(message: String): DownloadProgressInfo
}