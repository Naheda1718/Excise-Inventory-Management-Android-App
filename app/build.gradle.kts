plugins {
    id("com.android.application")
}

android {
    namespace = "com.excise.excisemanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.excise.excisemanagement"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")

    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")
    implementation ("org.apache.poi:poi:5.2.2")


}