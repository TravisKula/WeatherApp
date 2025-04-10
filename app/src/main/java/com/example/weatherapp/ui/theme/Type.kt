package com.example.weatherapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

val RacingSansOne = FontFamily(Font(R.font.racing_sans_one_regular))
val Aclonica = FontFamily(Font(R.font.aclonica_regular))


// Set of Starter Material typography styles
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RacingSansOne,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp

    ),
    titleLarge = TextStyle(
        fontFamily = Aclonica,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    labelLarge = TextStyle( // Defines label style explicitly
        fontFamily = RacingSansOne,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        color = Color.Black
    )
)

