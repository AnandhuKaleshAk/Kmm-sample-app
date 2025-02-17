package org.example.project.di



fun appModule() = listOf(provideHttpClientModule, provideRepositoryModule, provideviewModelModule)