package com.pawxy.mp3downloader.components.common.stepscontent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pawxy.mp3downloader.ui.theme.Blue
import com.pawxy.mp3downloader.ui.theme.Typography

@Composable
fun DownloadingContent(
    completePercentage: Float = 0f
) {
    val animatedProgress by animateFloatAsState(
        targetValue = completePercentage,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "percentage animation"
    )
    Column {
            Text(text = "Downloading...", style = Typography.bodyMedium)
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            color = Blue
        )
    }
}

@Preview
@Composable
fun DownloadContentPreview(){
    DownloadingContent()
}
