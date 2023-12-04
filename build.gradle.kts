
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://developer.huawei.com/repo/") }
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.0.0")
        classpath ("com.google.gms:google-services:4.3.14")
        classpath ("com.huawei.agconnect:agcp:1.9.1.300")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")

    }
}
allprojects {
    repositories {
        // Add the Maven address.
        google()
        jcenter()
        maven { url = uri("https://developer.huawei.com/repo/") }
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id ("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}