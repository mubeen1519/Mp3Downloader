package com.pawxy.mp3downloader.model.data

import android.util.Log
import com.pawxy.mp3downloader.mainModule
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StepsRepositoryImplementation @Inject constructor(
    moshi: Moshi
) : StepsRepository {
    private val videoInfoAdapter = moshi.adapter(VideoInfo::class.java)
    private val downloadInfoAdapter = moshi.adapter(DownloadProgressInfo::class.java)
    override suspend fun getInfo(url: String): Flow<Response<VideoInfo>> = flow {
        try {
            emit(Response.Loading)
            val output = mainModule().callAttr("get_video_info", url)
            Log.d("mubeen", "getInfo $output")
            val videoInfo: VideoInfo = videoInfoAdapter.fromJson(output.toString())
                ?: throw Exception("Error while parsing video info into json")
            emit(Response.Success(videoInfo))
        } catch (e: Exception) {
            emit(Response.Failure(e))
            Log.d("mubeen", "getInfo: error${e.message}")
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun downloadAudio(url : String): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            val output = mainModule().callAttr(
                "download_audio",
                url,
                this@StepsRepositoryImplementation::callBackFromMain
            )
            Log.d("mubeen", "downloadAudio: $output")
            emit(Response.Success(Unit))

        } catch (e: Exception) {
            emit(Response.Failure(e))
            Log.d("mubeen", "downloadAudio: error=${e}")
        }
    }.flowOn(Dispatchers.Default)

    override fun callBackFromMain(message: String): DownloadProgressInfo {
        return downloadInfoAdapter.fromJson(message)
            ?: throw Exception("Error while parsing download info from JSON")
    }
}