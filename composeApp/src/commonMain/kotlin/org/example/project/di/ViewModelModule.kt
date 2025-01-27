package org.example.project.di

import org.example.project.ui.auth.LoginViewModel
import org.koin.dsl.module

val provideviewModelModule = module {
    single {
        LoginViewModel(get())
    }
}