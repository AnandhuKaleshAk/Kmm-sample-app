package org.example.project.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.grotesk_bold
import kmm_sample_project.composeapp.generated.resources.grotesk_extra_bold
import kmm_sample_project.composeapp.generated.resources.grotesk_medium
import kmm_sample_project.composeapp.generated.resources.grotesk_regular
import org.jetbrains.compose.resources.Font


@Composable
fun CustomTypography():Typography {

    val groteskFamily = FontFamily(
        Font(Res.font.grotesk_regular, FontWeight.Normal),
        Font(Res.font.grotesk_medium, FontWeight.Medium),
        Font(Res.font.grotesk_bold, FontWeight.Bold),
        Font(Res.font.grotesk_extra_bold, FontWeight.ExtraBold)

    )
    return  Typography(
        h1 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        h3 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp
        ),
        h4 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        h5 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ),
        h6 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        body1 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        body2 = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        button = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        caption = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        overline = TextStyle(
            fontFamily = groteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
    )


}







