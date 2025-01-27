package org.example.project.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.example.project.ui.auth.LoginScreen

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController){

    navigation(route=Graph.AUTHENTICATION, startDestination = AuthScreen.Login.route){
        composable(route=AuthScreen.Login.route){
            LoginScreen()
        }

    }
}


sealed class AuthScreen(var route:String){

    data object Login : AuthScreen(route = "LOGIN")
    data object SignUp : AuthScreen(route = "SIGN_UP")
    data object Forgot : AuthScreen(route = "FORGOT")

}