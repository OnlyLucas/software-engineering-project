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
	implementation("org.springframework.boot:spring-boot-starter-log4j2:3.1.5")
	implementation("org.mindrot:jbcrypt:0.4")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
	implementation("org.springframework.boot:spring-boot-starter-security:3.2.0")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	// https://mvnrepository.com/artifact/org.springframework.security.oauth/spring-security-oauth2
	implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

configurations {
	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
}