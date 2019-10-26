import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

	// https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
    rootProject.extra["jaxbapiVersion"] = "2.3.1"
	
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
    rootProject.extra["jacksonModule_kotlinVersion"] = "2.10.0"
	
	// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    rootProject.extra["logbackVersion"] = "1.2.3"
	
	// https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    rootProject.extra["junit5Version"] = "5.5.2"
	
	// https://mvnrepository.com/artifact/org.amshove.kluent/kluent
	// https://mvnrepository.com/artifact/org.amshove.kluent/kluent?repo=springio-plugins-release
	// https://mvnrepository.com/artifact/org.amshove.kluent/kluent?repo=jcenter <- the latest
    rootProject.extra["kluent_Version"] = "1.56"

}

plugins {

	// https://plugins.gradle.org/plugin/com.github.ben-manes.versions
	id("com.github.ben-manes.versions") version "0.27.0"

	// https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-gradle-plugin
    kotlin("jvm") version "1.3.50"
	
	java
	application
	
	// https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
	id("com.github.johnrengelman.shadow") version "5.1.0"
	
	
}

repositories {

	jcenter()
	mavenLocal()
	mavenCentral()

	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}
	maven {
		url = uri("https://kotlin.bintray.com/kotlin-js-wrappers")
	}

}

dependencies {

	compile(kotlin("stdlib-jdk8"))
	implementation(kotlin("stdlib-jdk7"))
	implementation(kotlin("reflect"))

	compile("com.fasterxml.jackson.module:jackson-module-kotlin:${rootProject.extra["jacksonModule_kotlinVersion"]}")

	testImplementation(kotlin("test"))
	testImplementation(kotlin("test-junit"))

	implementation("javax.xml.bind:jaxb-api:${rootProject.extra["jaxbapiVersion"]}")

	compile("ch.qos.logback:logback-classic:${rootProject.extra["logbackVersion"]}")

	testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit5Version"]}")

	testImplementation("org.amshove.kluent:kluent:${rootProject.extra["kluent_Version"]}")


}

java {
	sourceCompatibility = JavaVersion.VERSION_12
	targetCompatibility = JavaVersion.VERSION_12
}

tasks.withType<KotlinCompile>().all {
	kotlinOptions {
		jvmTarget = "12"
		// freeCompilerArgs = listOf("-Xjsr305=strict")
	}
}

group = "vlfsoft"
version = 1.0 // -> vlfsoft.module-1.0.jar

val appPackage="graph-algorithms"

application {
    mainClassName = "vlfsoft.$appPackage.ApplicationKt"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = application.mainClassName
        attributes["Automatic-Module-Name"] = "vlfsoft.$appPackage.app"
    }
}

tasks {

	// https://stackoverflow.com/questions/31405818/want-to-specify-jar-name-and-version-both-in-build-gradle
	// https://github.com/barlog-m/spring-boot-2-example-app/blob/master/build.gradle.kts
	// https://stackoverflow.com/questions/55575264/creating-a-fat-jar-in-gradle-with-kotlindsl
    shadowJar {
        // defaults to project.name
        archiveBaseName.set("vlfsoft.$appPackage.app")

        // defaults to all, so removing this overrides the normal, non-fat jar
        // archiveClassifier.set("")
    }
}