import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)


}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)


        }
        androidUnitTest.dependencies {
            implementation(libs.mockk.android) // MockK for Android
            implementation("app.cash.turbine:turbine:1.0.0") // For Flow testing


        // For JUnit 5
        }

        androidInstrumentedTest.dependencies {
            implementation("junit:junit:4.13.2")
            implementation("androidx.compose.ui:ui-test-junit4:1.0.5")
            implementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
            runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

            implementation("androidx.compose.ui:ui-test-junit4:1.5.4")


            // Coroutines Test
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

            // Compose UI testing manifest
            implementation("androidx.compose.ui:ui-test-manifest:1.5.4")

            implementation(libs.mockk.android) // MockK for Android

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.navigation.compose)


            //ktor bundle
            implementation(libs.bundles.ktor)

            //koin dependency
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.datetime)



        }
        commonTest.dependencies {
            implementation(kotlin("test")) // Kotlin Test
            implementation(libs.kotlinx.coroutines.test) // Coroutine Test
            implementation(libs.turbine)
            implementation(libs.bundles.shared.commonTest)

            implementation(libs.mockk.common)


            // Test helper



        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

        }
        iosTest.dependencies {

        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    debugImplementation(compose.uiTooling)
}

