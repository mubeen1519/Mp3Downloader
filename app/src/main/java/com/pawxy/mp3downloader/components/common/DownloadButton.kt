package com.pawxy.mp3downloader.components.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pawxy.mp3downloader.components.homescreen.HomeScreenStep
import com.pawxy.mp3downloader.ui.theme.Purple

@Composable
fun DownloadButton(step: HomeScreenStep, modifier: Modifier = Modifier, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = step in listOf(
            HomeScreenStep.INITIAL,
            HomeScreenStep.SUCCESS,
            HomeScreenStep.FAILED
        ),
    ) {
        if (step == HomeScreenStep.INITIAL) {
            Text(text = "Download", modifier = Modifier.padding(16.dp))
        } else if (step == HomeScreenStep.GRABBING) {
            Row {
                CircularProgressIndicator()
                Text(text = "Grabbing Info...", modifier = Modifier.padding(16.dp))
            }
        } else if ((step == HomeScreenStep.SUCCESS) or (step == HomeScreenStep.FAILED)) {
            Text(text = "Download Another MP3", modifier = Modifier.padding(16.dp))
        }
    }
}