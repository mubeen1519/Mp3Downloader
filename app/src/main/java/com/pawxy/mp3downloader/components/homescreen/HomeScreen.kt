package com.pawxy.mp3downloader.components.homescreen

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.pawxy.mp3downloader.components.common.DestinationFolderContent
import com.pawxy.mp3downloader.components.common.DownloadButton
import com.pawxy.mp3downloader.components.common.TextField
import com.pawxy.mp3downloader.components.common.VideoInfoCard
import com.pawxy.mp3downloader.components.common.stepscontent.ConvertingContent
import com.pawxy.mp3downloader.components.common.stepscontent.DownloadingContent
import com.pawxy.mp3downloader.components.common.stepscontent.FailureScreenContent
import com.pawxy.mp3downloader.components.common.stepscontent.SaveContent
import com.pawxy.mp3downloader.components.common.stepscontent.SuccessScreenContent
import java.io.File
import java.net.URLDecoder

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val path = context.filesDir.canonicalPath

    state.toastErrorMessage?.let { message ->
        LaunchedEffect(key1 = message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.toastErrorMessageShown()
        }
    }

    state.step.let { step ->
        LaunchedEffect(key1 = step) {
            Log.d("mubeen", "current step $step")
            when (step) {
                HomeScreenStep.GRABBING -> viewModel.getInfo()
                HomeScreenStep.DOWNLOADING -> viewModel.downloadAudio()
                HomeScreenStep.CONVERTING -> viewModel.convertAudio(path)
                HomeScreenStep.SAVING -> {
                    //save file into selected device directory
                            val filename = state.videoInfo!!.formattedTitle + ".mp3"
                            val myInternalFile =
                                File(context.filesDir.canonicalPath + File.separator + filename)
                            val documentFile =
                                DocumentFile.fromTreeUri(context, state.destinationFolder)
                            val myExternalFile: DocumentFile? =
                                documentFile?.createFile("audio/mpeg", filename)
                    if (myExternalFile != null) {
                        viewModel.saveAudio(myInternalFile,myExternalFile,context)
                    }
                }
                else -> {}
            }
        }
    }

    //launcher for opening and selecting the document folder from device
    val destinationPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = {
            if (it != null) {
                viewModel.onEvent(HomeScreenEvents.OnDestinationFolderChange(it))
            }
        }
    )
    //for requesting permission
    val externalStoragePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Log.d("mubeen", "HomeScreen: $isGranted")
            viewModel.onEvent(HomeScreenEvents.OnVideoGrabInfo(rootPath = path))
        }
    )
    Column {
        if (state.step in listOf(
                HomeScreenStep.INITIAL,
                HomeScreenStep.GRABBING
            )
        ) {
            Text(text = "YouTube Link", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.youtubeLink,
                enabled = state.step == HomeScreenStep.INITIAL,
                onValueChange = { viewModel.onEvent(HomeScreenEvents.OnYoutubeLinkChange(it)) },
                onDeleteClick = { viewModel.onEvent(HomeScreenEvents.OnYoutubeLinkChange("")) })
            Spacer(modifier = Modifier.height(16.dp))

            DestinationFolderContent(
                onClick = { destinationPicker.launch(Uri.parse("root")) },
                enabled = state.step == HomeScreenStep.INITIAL,
                displayPath = if (state.destinationFolder.toString()
                        .isEmpty()
                ) "Select destination" else getHumanReadableUri(
                    state.destinationFolder
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
            DownloadButton(step = state.step) {
                if (state.youtubeLink.isBlank()) {
                    Toast.makeText(context, "Link is empty", Toast.LENGTH_SHORT).show()
                } else if (state.destinationFolder == Uri.EMPTY) {
                    Toast.makeText(context, "Destination folder is empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    externalStoragePermissionResultLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
            }
        } else {
            state.videoInfo?.let { videoInfo ->
                Column {
                    VideoInfoCard(videoInfo = videoInfo)
                    Spacer(modifier = Modifier.height(24.dp))
                    when (state.step) {
                        HomeScreenStep.DOWNLOADING -> {
                            DownloadingContent(completePercentage = state.downloadPercentComplete)
                        }
                        HomeScreenStep.CONVERTING -> {
                            ConvertingContent()
                        }
                        HomeScreenStep.SAVING -> {
                            SaveContent()
                        }
                        HomeScreenStep.SUCCESS -> {
                            SuccessScreenContent {
                                viewModel.onEvent(HomeScreenEvents.OnDownloadAnotherButtonClick)
                            }
                        }
                        HomeScreenStep.FAILED -> {
                            FailureScreenContent(errorMessage = state.errorMessage) {
                                viewModel.onEvent(HomeScreenEvents.OnDownloadAnotherButtonClick)
                            }
                        }
                        else -> {
                        }
                    }
                }
            }

        }
    }
}

fun getHumanReadableUri(uri: Uri): String {
    val decodedPath = URLDecoder.decode(uri.path, "UTF-8")
    return decodedPath.substringAfterLast("/")
}
