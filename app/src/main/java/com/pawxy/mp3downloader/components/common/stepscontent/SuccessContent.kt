package com.pawxy.mp3downloader.components.common.stepscontent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawxy.mp3downloader.components.common.DownloadButton
import com.pawxy.mp3downloader.components.homescreen.HomeScreenStep
import com.pawxy.mp3downloader.ui.theme.Green
import com.pawxy.mp3downloader.ui.theme.Yellow

@Composable
fun SuccessScreenContent(successProgress : Float = 0f,onClick : () -> Unit) {
    val animatedProgress by animateFloatAsState(
        targetValue = successProgress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "percentage animation"
    )
    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Success", style = MaterialTheme.typography.bodyMedium)
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Success icon",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Green

                )
            }
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    color = Green,
                    trackColor = Green
                )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "info text",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Mp3 successfully saved into selected folder",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp
                )
            }
        }
        DownloadButton(
            step = HomeScreenStep.SUCCESS,
            onClick = onClick,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
@Preview
@Composable
fun Prev(){
    SuccessScreenContent {}
}