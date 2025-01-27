package org.example.project.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.compose_multiplatform
import kmm_sample_project.composeapp.generated.resources.email
import kmm_sample_project.composeapp.generated.resources.forgot_pass_title
import kmm_sample_project.composeapp.generated.resources.login_title
import kmm_sample_project.composeapp.generated.resources.password
import org.example.project.Dimens
import org.example.project.showToast
import org.example.project.utils.PiProgressIndicator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin

@Composable fun LoginScreen(){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoader by remember { mutableStateOf(false) }



    var isLoginClicked by remember { mutableStateOf(false) }

val viewModel:LoginViewModel= getKoin().get()

    val loginScreenState by viewModel.loginViewState.collectAsState()

//
    if(isLoginClicked){
        when(loginScreenState){
            is LoginViewModel.LoginScreenState.Loading -> {
                showLoader=true
            }
            is LoginViewModel.LoginScreenState.Success -> {
                showLoader=false
            }
            is LoginViewModel.LoginScreenState.Error -> {
                val error=loginScreenState as LoginViewModel.LoginScreenState.Error
                showToast(error.toString())
                isLoginClicked=false
                showLoader=false

            }
        }
    }

    if(showLoader){
        Spacer(modifier = Modifier.height(32.dp))
        PiProgressIndicator()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Logo",
            modifier = Modifier
                .height(Dimens.sdp120)
                .padding(bottom = Dimens.sdp32)
        )

        Text(text= stringResource(Res.string.login_title), modifier = Modifier, style =
        MaterialTheme.typography.h1)

        Spacer(modifier = Modifier.height(Dimens.sdp16))

        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            label = { Text(stringResource(Res.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password=it },
            label = { Text(stringResource(Res.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(Dimens.sdp16))

        Button(
            onClick = {
               isLoginClicked=true
                viewModel.login(email,password)
            },
            contentPadding = PaddingValues(
                start = Dimens.sdp8,
                top = Dimens.sdp16,
                end = Dimens.sdp8,
                bottom = Dimens.sdp16
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(Res.string.login_title), style = MaterialTheme.typography.h6)
        }

        Spacer(modifier = Modifier.height(Dimens.sdp16))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(Res.string.forgot_pass_title),
                modifier = Modifier.clickable {},
                color = MaterialTheme.colors.primary
            )


            Text(
                text = "Sign Up",
                modifier = Modifier.clickable {

                },
                color = MaterialTheme.colors.primary
            )

        }

    }
}


