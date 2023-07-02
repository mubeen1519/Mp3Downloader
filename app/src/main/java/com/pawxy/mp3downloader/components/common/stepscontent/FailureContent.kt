package com.pawxy.mp3downloader.components.common.stepscontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawxy.mp3downloader.components.common.DownloadButton
import com.pawxy.mp3downloader.components.homescreen.HomeScreenStep
import com.pawxy.mp3downloader.ui.theme.LightBlue
import com.pawxy.mp3downloader.ui.theme.LightRed

@Composable
fun FailureScreenContent(failureProgress : Float = 0f,errorMessage: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Failure",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LightRed
                )
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "failure",
                    modifier = Modifier.size(18.dp),
                    tint = LightRed
                )
            }
            LinearProgressIndicator(
                progress = failureProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = LightRed,
                trackColor = LightRed
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "error icon",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = errorMessage,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp
                )
            }
        }
        DownloadButton(
            step = HomeScreenStep.FAILED,
            onClick = onClick,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}