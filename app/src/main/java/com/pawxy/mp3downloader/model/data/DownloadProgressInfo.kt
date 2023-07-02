package com.pawxy.mp3downloader.model.data

import com.squareup.moshi.Json

data class DownloadProgressInfo(
    @field:Json(name = "downloaded_bytes")
    val downloadedBytes: Long,
    @field:Json(name = "total_bytes_estimate")
    val totalByteEstimate: Long,
    @field:Json(name = "eta")
    val eta : Long,
    @field:Json(name = "speed")
    val progressSpeed : Double,
){
val percentComplete : Float
    get() = if (totalByteEstimate != 0L) {
        (downloadedBytes.toFloat() / totalByteEstimate.toFloat())
    } else {
        0.0f
    }
}