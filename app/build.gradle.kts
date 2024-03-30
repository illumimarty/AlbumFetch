import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val apikeyPropertiesFile: File = rootProject.file("secrets.properties")
val apikeyProperties = Properties()
apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    namespace = "com.mnodado.albumfetch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mnodado.albumfetch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        buildFeatures {
            buildConfig = true
        }
        debug {
            buildConfigField("String", "API_KEY", "${apikeyProperties["API_KEY"]}")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "${apikeyProperties["API_KEY"]}")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.codepath.libraries:asynchttpclient:2.2.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}