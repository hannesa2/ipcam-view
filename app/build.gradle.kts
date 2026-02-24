plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.github.niqdev.ipcam"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.github.niqdev.ipcam"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "3.0"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles.addAll(
                listOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    file("proguard-rules.pro"),
                ),
            )
        }
    }
    lint {
        abortOnError = false
        disable += "MissingTranslation" + "InvalidPackage"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

dependencies {
    implementation(project(":mjpeg-view"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.22")
}
