// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //alias(libs.plugins.kotlin.compose) apply false
   // id("androidx.room") version "2.7.1" apply false
    //kotlin("jvm") version "1.9.24"
    alias(libs.plugins.com.google.devtools.ksp).apply(false)
    alias(libs.plugins.com.google.dagger.hilt.android).apply(false)
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}