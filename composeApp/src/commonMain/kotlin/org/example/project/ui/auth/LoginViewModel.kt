package org.example.project.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.project.data.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.base.doOnFailure
import org.example.project.base.doOnLoading
import org.example.project.base.doOnSuccess

class LoginViewModel(private val authRepository: AuthRepository):ViewModel() {

    private val _loginViewState = MutableStateFlow<LoginScreenState>(LoginScreenState.Loading)
    val loginViewState: StateFlow<LoginScreenState> = _loginViewState


    fun login(email:String,password:String){
        viewModelScope.launch {
            authRepository.login(email,password).doOnSuccess {
                _loginViewState.value = it?.let { LoginScreenState.Success(it) }!!

            }.doOnFailure {
                    errors->
                _loginViewState.value = errors?.error?.let { LoginScreenState.Error(it) }!!


            }.doOnLoading {
                _loginViewState.value = LoginScreenState.Loading
            }.collect{

            }
        }
    }

    sealed class LoginScreenState{
        data object Loading:LoginScreenState()
        data class Error(val errorMessage:String?):LoginScreenState()
        data class  Success(val responseData: LoginResponse):LoginScreenState()
    }
}