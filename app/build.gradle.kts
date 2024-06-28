import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.Properties
import java.io.FileInputStream
plugins {
//    id("org.jetbrains.kotlin.plugin.compose")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.kapt" )
//    id("org.jetbrains.kotlin.plugin.parcelize")
    id("kotlin-parcelize")
//    kotlin("jvm")
}

val configProperties = Properties()
configProperties.load(FileInputStream(rootProject.file("config.properties")))

val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(rootProject.file("keystore.properties")))

android {
    namespace = "com.example.myapplication"
    compileSdk = configProperties.getProperty("compileSdkVersion").toInt()
    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
            abiFilters.add( "x86_64")
        }

    }

    sourceSets {
        getByName("main") {
            res.srcDirs("src/main/res")
        }
        getByName("debug") {
            res.srcDirs("res/mc")
        }

    }


    signingConfigs {
        create("config") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            // 启用代码压缩、优化及混淆
            isMinifyEnabled = false
            // 启用资源压缩，需配合 minifyEnabled=true 使用
            isShrinkResources = false
            // 指定混淆保留规则
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("config")
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfigs.getByName("config")
            //noinspection ChromeOsAbiSupport
            ndk.abiFilters += "x86"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig= true

    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions += "tier"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "34.0.0"

    productFlavors {
        create("free") {
            //应用包名添加后缀
            applicationIdSuffix = ".free"
            //关联维度
            dimension = "tier"
            manifestPlaceholders["app_channel_value"] = name
            manifestPlaceholders["app_name_value"] = "Android"
            buildConfigField("String", "BASE_URL", "\"http://192.168.20.119:8050\"")
            buildConfigField("String", "SOCKET_URL", "\"sd:tcp://192.168.20.119:8002\"")
//            buildConfigField("String", "BASE_URL", "\"http://192.168.20.119:8082\"")
        }
        create("pro") {
            //应用包名添加后缀
            applicationIdSuffix = ".pro"
            //关联维度
            dimension = "tier"
            manifestPlaceholders["app_channel_value"] = name
            manifestPlaceholders["app_name_value"] = "Android"
            buildConfigField("String", "BASE_URL", "\"http://121.40.62.167:8000\"")
            buildConfigField("String", "SOCKET_URL", "\"sd:tcp://121.40.62.167:8002\"")
        }
    }
    applicationVariants.all {
        outputs.all {
            if (this is BaseVariantOutputImpl) {
                val name = "B-Im-${buildType.name}-${versionName}-${productFlavors.first().name}.apk"
                outputFileName = name
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("org.slf4j:slf4j-simple:2.0.12")

//    implementation("org.noear:solon:2.7.5")
    implementation("org.noear:snack3:3.2.95")
    implementation("org.noear:socketd-transport-smartsocket:2.5.0")

    //noinspection GradleDependency
    //noinspection GradleDependency
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")
    implementation("androidx.webkit:webkit:1.11.0")


    val room_version = "2.6.1"

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    //noinspection GradlePluginVersion
//    implementation("com.android.tools.build:gradle:8.2.2")
//    implementation("com.google.accompanist:accompanist-permissions:0.31.0-alpha")
//    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.0-alpha")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.core:core-ktx:1.12.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation("androidx.activity:activity-compose:1.8.0")

    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

//    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11'")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")


    testImplementation("junit:junit:4.13.2")
    // Testing Nav()
    testImplementation("androidx.navigation:navigation-testing:2.7.7")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}