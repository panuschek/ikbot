plugins {
	kotlin("jvm") version "2.1.10"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"

	id("com.google.devtools.ksp") version "2.1.10-1.0.31"
	id("eu.vendeli.telegram-bot") version "8.0.0"
}

group = "io.github.panuschek"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.api-client:google-api-client:2.7.2")
	implementation("com.google.oauth-client:google-oauth-client-jetty:1.39.0")
	implementation("com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0")

	implementation("eu.vendeli:spring-ktgram-starter:8.0.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

configurations.all {
	resolutionStrategy.eachDependency {
		val serdeVer = "1.8.1"
		when(requested.module.toString()) {
			// json serialiazaton
			"org.jetbrains.kotlinx:kotlinx-serialization-json" -> useVersion(serdeVer)
			"org.jetbrains.kotlinx:kotlinx-serialization-json-jvm" -> useVersion(serdeVer)
			"org.jetbrains.kotlinx:kotlinx-serialization-core" -> useVersion(serdeVer)
			"org.jetbrains.kotlinx:kotlinx-serialization-core-jvm" -> useVersion(serdeVer)
			"org.jetbrains.kotlinx:kotlinx-serialization-bom" -> useVersion(serdeVer)
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
