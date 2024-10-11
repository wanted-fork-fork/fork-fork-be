import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    kotlin("kapt") version "1.9.23"
    id("org.jetbrains.kotlinx.kover") version "0.8.2"
}

group = "com.fork"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

val springCloudVersion = "2023.0.1"
val swaggerVersion = "2.2.0"
val mockkVersion = "1.13.11"
val jwtVersion = "0.11.5"
val kotlinLoggingVersion = "5.1.0"
val awsS3Version = "1.12.729"
val mapStructVersion = "1.5.5.Final"
val springMockkVersion = "4.0.2"
val bucket4jVersion = "8.10.1"
val jaxbVersion = "2.3.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // Logging
    implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggingVersion")

    // Jwt
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3
    implementation("com.amazonaws:aws-java-sdk-s3:$awsS3Version")
    implementation("javax.xml.bind:jaxb-api:$jaxbVersion")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
    kaptTest("org.mapstruct:mapstruct-processor:$mapStructVersion")

    // rate limiter
    implementation("com.bucket4j:bucket4j-core:$bucket4jVersion")

    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kover {
    reports {
        filters {
            excludes {
                classes("**.*Config*", "**.*Application*", "**.*enum*")
            }
        }
    }
}
