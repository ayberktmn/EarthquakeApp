
buildscript {
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.4")
        classpath ("com.google.gms:google-services:4.3.14")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")

    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}