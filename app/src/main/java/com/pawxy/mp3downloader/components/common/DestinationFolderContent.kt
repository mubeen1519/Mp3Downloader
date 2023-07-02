package com.pawxy.mp3downloader.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawxy.mp3downloader.ui.theme.LightGray

@Composable
fun DestinationFolderContent(onClick: () -> Unit, enabled: Boolean, displayPath: String) {
    Column {
        Text(text = "Destination Folder", style = MaterialTheme.typography.bodyMedium)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable(enabled = enabled, onClick = onClick),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = 1.dp, color = Color.White),
            colors = CardDefaults.cardColors(containerColor = LightGray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Folder, contentDescription = "Folder Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = displayPath,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "info icon",
                modifier = Modifier.size(18.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Where you want to save the MP3",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp
            )
        }
    }
}