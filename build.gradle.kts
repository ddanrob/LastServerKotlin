plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("kapt") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "dev.velocity"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.google.code.gson:gson:2.10.1")
}

kotlin {
    jvmToolchain(21) // Using Java 21
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.shadowJar {
    archiveBaseName.set("LastServer-Kotlin")
    archiveClassifier.set("")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    dependencies {
        include(dependency("org.jetbrains.kotlin:.*"))
        include(dependency("org.jetbrains.kotlinx:.*"))
        include(dependency("com.google.code.gson:.*"))
    }
}

kapt {
    useBuildCache = false
    arguments {
        arg("kapt.kotlin.generated", "$buildDir/generated/source/kapt/main")
    }
}