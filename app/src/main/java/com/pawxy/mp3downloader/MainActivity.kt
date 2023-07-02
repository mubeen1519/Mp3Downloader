package com.pawxy.mp3downloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.pawxy.mp3downloader.components.common.HeaderContent
import com.pawxy.mp3downloader.components.homescreen.HomeScreen
import com.pawxy.mp3downloader.ui.theme.Mp3DownloaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mp3DownloaderTheme {
              MainActivityContent()
            }
        }
    }
}

@Preview
@Composable
fun MainActivityContent() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        HeaderContent()
        Spacer(modifier = Modifier.height(24.dp))
        HomeScreen()
    }
}

