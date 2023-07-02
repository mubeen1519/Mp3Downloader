package com.pawxy.mp3downloader.components.homescreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.pawxy.mp3downloader.model.data.StepsRepository
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    moshi: Moshi,
    private val stepsRepository: StepsRepository
) : ViewModel() {
    var state by mutableStateOf(HomeScreenState())
        private set
    private var isLoading: Boolean = false

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnYoutubeLinkChange -> {
                state = state.copy(youtubeLink = event.youtubeLink)
            }

            is HomeScreenEvents.OnDestinationFolderChange -> {
                state = state.copy(destinationFolder = event.destinationFolder)
                Log.d("mubeen", "DestinationFolderChange ${event.destinationFolder}")
            }

            is HomeScreenEvents.OnVideoGrabInfo -> {
                state = state.copy(step = HomeScreenStep.GRABBING)
            }

            is HomeScreenEvents.OnDownloadAnotherButtonClick -> {
                state = state.copy(step = HomeScreenStep.INITIAL)
            }
        }
    }

    fun getInfo() {
        viewModelScope.launch {
            stepsRepository.getInfo(state.youtubeLink).collect { response ->
                when (response) {
                    is com.pawxy.mp3downloader.model.data.Response.Loading -> {
                        isLoading = true
                    }

                    is com.pawxy.mp3downloader.model.data.Response.Success -> {
                        isLoading = false
                        var videoInfo = response.data
                        // This is done because when saving a file, it replaces | with ｜
                        videoInfo = videoInfo?.copy(
                            title = videoInfo.title.replace('|', '｜')
                        )
                        state = state.copy(
                            step = HomeScreenStep.DOWNLOADING,
                            videoInfo = videoInfo
                        )
                    }

                    is com.pawxy.mp3downloader.model.data.Response.Failure -> {
                        isLoading = false
                        val error = response.e
                        // Handle failure state and display error message
                        state = state.copy(
                            step = HomeScreenStep.INITIAL,
                            toastErrorMessage = "Failed to get the video info",
                            videoInfo = null
                        )
                        Log.d("mubeen", "getInfo: error ${error?.message}")
                    }
                }
            }
        }
    }

    fun downloadAudio() {
        viewModelScope.launch {
            stepsRepository.downloadAudio(state.youtubeLink).collect { response ->
                when (response) {
                    is com.pawxy.mp3downloader.model.data.Response.Loading -> {
                        isLoading = true
                    }

                    is com.pawxy.mp3downloader.model.data.Response.Success -> {
                        isLoading = false
                        state = state.copy(
                            step = HomeScreenStep.CONVERTING,
                        )
                    }

                    is com.pawxy.mp3downloader.model.data.Response.Failure -> {
                        isLoading = false
                        val error = response.e
                        goToFailureScreen("Error while downloading: ${error?.message}")
                    }
                }
            }
        }
    }

    fun convertAudio(path: String) {
        viewModelScope.launch {
            val inputFile = "${path + File.separator}\"${state.videoInfo!!.title}.m4a\""
            val outputFile = "${path + File.separator}\"${state.videoInfo!!.formattedTitle}.mp3\""
            Log.d("mubeen", "input file : $inputFile")
            Log.d("mubeen", "output file : $outputFile")

            if (inputFile != outputFile) {
                Config.resetStatistics()
                Config.enableStatisticsCallback { stats ->
                    Log.d("mubeen", "statistics $stats")
                }
            }
            FFmpeg.executeAsync("-i $inputFile -c:v copy -c:a libmp3lame -q:a 4 $outputFile")
            { _, returnCode ->
                when (returnCode) {
                    Config.RETURN_CODE_SUCCESS -> {
                        Log.i("mubeen", "Async command executed successfully")
                        //delete old file
                        File(inputFile).delete()
                        viewModelScope.launch {
                            delay(1000)
                            withContext(Dispatchers.Main) {
                                state = state.copy(
                                    step = HomeScreenStep.SAVING
                                )
                            }
                        }
                    }

                    Config.RETURN_CODE_CANCEL -> {
                        Log.i("mubeen", "command execution cancelled by user")
                    }

                    else -> {
                        Log.i("mubeen", "command execution failed with return Code=$returnCode")
                        viewModelScope.launch {
                            goToFailureScreen("Error while converting")
                        }
                    }
                }

            }
        }
    }

    fun saveAudio(internalFile: File, externalFile: DocumentFile, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    if (internalFile.exists()) {
                        val fis = FileInputStream(internalFile)
                        val fos: OutputStream? =
                            externalFile.uri.let { context.contentResolver.openOutputStream(it) }

                        if (fos != null) {
                            val buffer = ByteArray(1024)
                            var bytesRead: Int = fis.read(buffer)

                            while (bytesRead != -1) {
                                fos.write(buffer, 0, bytesRead)
                                bytesRead = fis.read(buffer)
                            }
                            // Close the streams
                            fis.close()
                            fos.close()

                            internalFile.delete()
                            goToSuccessScreen()
                        } else {
                            goToFailureScreen("Failed to open output stream for external file.")
                        }
                    } else {
                        goToFailureScreen("File not found.")

                    }
                } catch (e: Exception) {
                    goToFailureScreen(e.message)
                }

            }
        }
    }

    fun toastErrorMessageShown() {
        state = state.copy(toastErrorMessage = null)
    }

    private suspend fun goToSuccessScreen() {
        delay(1000)
        Log.d("mubeen", "success: ")
        state = state.copy(step = HomeScreenStep.SUCCESS)
    }

    private suspend fun goToFailureScreen(errorMessage: String?) {
        delay(1000)
        withContext(Dispatchers.Main) {
            state = state.copy(
                step = HomeScreenStep.FAILED,
                errorMessage = errorMessage ?: "Unknown error"
            )
        }
    }

}