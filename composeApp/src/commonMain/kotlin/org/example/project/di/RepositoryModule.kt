package org.example.project.di


import org.example.project.network.ApiService
import org.example.project.ui.auth.AuthRepository
import org.koin.dsl.module

val provideRepositoryModule = module {

        single<AuthRepository> { AuthRepository(get()) }
    factory {
        ApiService(get())
    }


}