plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id ("kotlin-parcelize")
    id("com.huawei.agconnect")

}

android {
    namespace = "com.ayberk.earthquakeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ayberk.earthquakeapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file ("anahtarearthquake.jks")
            keyAlias = "key"
            keyPassword = "123456"
            storePassword = "123456"
            enableV1Signing = true
            enableV2Signing = true
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            packagingOptions{
                doNotStrip ("*/arm64-v8a/libucs-credential.so")
                doNotStrip ("*/armeabi-v7a/libucs-credential.so")
                // If the CPU architecture is x86, add the following configuration:
                doNotStrip ("*/x86/libucs-credential.so")
                doNotStrip ("*/x86_64/libucs-credential.so")
                exclude ("lib/x86/libucs-credential.so")
                // Exclude the SO file on which the x86_64 platform depends.
                exclude ("lib/x86_64/libucs-credential.so")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")

    //annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'

    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.25")

    implementation ("com.squareup.picasso:picasso:2.8")

    implementation("androidx.navigation:navigation-fragment-ktx:")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.25")
    implementation("androidx.navigation:navigation-ui-ktx:")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")

    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    implementation ("com.huawei.hms:location:6.12.0.300")
    implementation ("com.huawei.hms:maps:6.11.2.301")
    implementation ("com.huawei.agconnect:agconnect-core:1.9.1.300")

}