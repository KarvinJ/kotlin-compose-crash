plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "knight.nameless.compose"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "knight.nameless.compose"
        minSdk = 28
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
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)

//    lifeCycle coroutines dependencies.
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.compose.material.icons.extended)

    implementation(libs.ktor.client.okhttp)

    //            Ktor. Since we bundle several libraries with the ktor, then we
//            just need to call ktor to import them all.
    implementation(libs.bundles.ktor)
    implementation(libs.ktor.serialization.kotlinx.json)


//    Room database dependencies
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)



//    now i now that I can have dependencies from other format without any problem.
//    Need to check if there is a way to adapt this dependency to mantain the same structures of the other dependencies.
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")

//    Debug/Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}