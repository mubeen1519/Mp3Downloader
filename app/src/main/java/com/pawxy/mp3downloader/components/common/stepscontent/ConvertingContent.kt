package com.pawxy.mp3downloader.components.common.stepscontent

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pawxy.mp3downloader.ui.theme.Typography

@Composable
fun ConvertingContent(
) {
    Column {
        Text(text = "Converting...", style = Typography.bodyMedium)
    }
}