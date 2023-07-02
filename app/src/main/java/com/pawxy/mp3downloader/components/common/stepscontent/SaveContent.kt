package com.pawxy.mp3downloader.components.common.stepscontent

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pawxy.mp3downloader.ui.theme.Typography

@Composable
fun SaveContent(
) {
    Column {
        Text(text = "Saving...", style = Typography.bodyMedium)
    }

}