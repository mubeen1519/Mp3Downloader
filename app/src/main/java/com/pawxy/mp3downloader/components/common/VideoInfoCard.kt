package com.pawxy.mp3downloader.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pawxy.mp3downloader.model.data.VideoInfo
import com.pawxy.mp3downloader.model.data.simplifyAmount
import com.pawxy.mp3downloader.ui.theme.LightGray

@Composable
fun VideoInfoCard(videoInfo: VideoInfo) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            AsyncImage(
                model = videoInfo.thumbnail,
                contentDescription = "thumbnail",
                contentScale = ContentScale.Inside,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = videoInfo.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = "visibility icon",
                    modifier = Modifier.padding(end = 8.dp).size(20.dp),
                    tint = Color.Gray
                )
                Text(
                    text = "${videoInfo.totalViewCount.simplifyAmount()} Views",
                    color = Color.Gray
                )

                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "like icon",
                    modifier = Modifier.padding(start = 24.dp, end = 8.dp).size(20.dp),
                    tint = Color.Gray
                )
                Text(text = "${videoInfo.totalLikeCount.simplifyAmount()} Likes", color = Color.Gray)

            }
        }
    }
}

@Preview
@Composable
fun PreviewVideoInfoItem() {
    VideoInfoCard(
        videoInfo = VideoInfo(
            thumbnail = "https://i.ytimg.com/vi/5Eqb_-j3FDA/maxresdefault.jpg",
            title = "Coke Studio | Season 14 | Pasoori | Ali Sethi x Shae Gill",
            totalViewCount = 592239063,
            totalLikeCount = 7200362
        )
    )
}