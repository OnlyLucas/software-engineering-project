plugins {
	java
	id("org.springframework.boot") version "3.2.0-RC2"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.flatfusion"
version = "0.0.1-SNAPSHOT"

java {
//	sourceCompatibility = JavaVersion.VERSION_17
//	targetCompatibility = JavaVersion.VERSION_17
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jetty
	// implementation("org.springframework.boot:spring-boot-starter-jetty:3.1.5")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
	implementation("com.mysql:mysql-connector-j:8.2.0")
	//implementation("org.springframework.boot:spring-boot-starter-log4j2:3.1.5")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

configurations {
	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
}