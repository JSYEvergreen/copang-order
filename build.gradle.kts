import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    id("org.springframework.boot") version "2.7.10" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
}

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")

    dependencyManagement {
        imports {
            mavenBom("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
        }
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.github.microutils:kotlin-logging:2.1.23")
        testImplementation("io.mockk:mockk:1.13.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.5")
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
