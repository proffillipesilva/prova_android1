plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    // 1. Em vez de KSP, use 'kotlin-kapt' (já vem embutido no Kotlin!)
    // Adicione esta linha:
    alias(libs.plugins.ksp)
}

android {
    namespace = "br.com.fiec.appsimulado"
    compileSdk {
        version = release(37)
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "br.com.fiec.appsimulado"
        minSdk = 34
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.room3.common)
    implementation(libs.androidx.room3.runtime)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    ksp("androidx.room3:room3-compiler:3.0.1")

    // OkHttp e Logging Interceptor (Padrão para monitorar e depurar requisições)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Kotlin Coroutines (Padrão para chamadas assíncronas)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")


    // 1. Ícones Estendidos do Material Design (resolve import androidx.compose.material.icons.filled.Delete)
    // Se você estiver utilizando o Compose BOM no seu projeto, não precisa especificar a versão:
    implementation("androidx.compose.material:material-icons-extended")

    // 2. Coil para Jetpack Compose (resolve import coil.compose.AsyncImage)
    implementation("io.coil-kt:coil-compose:2.6.0")
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}