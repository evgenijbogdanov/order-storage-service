import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	val kotlinPluginVersion = "1.6.10"
	kotlin("jvm") version kotlinPluginVersion
	kotlin("plugin.spring") version kotlinPluginVersion
	kotlin("kapt") version "1.6.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val mockkVersion = "1.12.2"
val springmockkVersion = "3.1.0"
val testcontainersVersion = "1.16.3"
val mapstructVersion = "1.4.2.Final"

repositories {
	mavenCentral()
}

dependencies {
	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	// jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	// database
	implementation("org.springframework:spring-jdbc")
	runtimeOnly("io.r2dbc:r2dbc-postgresql")
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")
	// mapstruct
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
	// logging
	modules {
		module("org.springframework.boot:spring-boot-starter-logging") {
			replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
		}
	}
}

// test
dependencies {
	// spring
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:$mockkVersion")
	// reactor
	testImplementation("io.projectreactor:reactor-test")
	// testcontainers
	testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
	testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
	testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}