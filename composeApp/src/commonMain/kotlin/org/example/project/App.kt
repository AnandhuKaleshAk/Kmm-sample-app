package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.compose_multiplatform
import org.example.project.theme.MyAppTheme

@Composable
@Preview
fun App() {
  MyAppTheme {

      Column(modifier = Modifier.background(MaterialTheme.colors.primary).
      fillMaxWidth().fillMaxHeight()) {

          Text(
              text = "Hello, Jetpack Compose with Custom Typography!",
              style = MaterialTheme.typography.h5 // Use any style like h1, body1, etc.
          )

      }

  }
}