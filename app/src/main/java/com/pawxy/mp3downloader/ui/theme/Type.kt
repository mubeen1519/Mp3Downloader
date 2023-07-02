package com.pawxy.mp3downloader.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pawxy.mp3downloader.R

// Set of Material typography styles to start with

val jakarta_sans = FontFamily(
    Font(R.font.plus_jakarta_sans_semibold, style = FontStyle.Normal),
    Font(R.font.plus_jakarta_sans_bold, style = FontStyle.Normal)
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = jakarta_sans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = jakarta_sans,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = jakarta_sans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)