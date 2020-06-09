import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    java
    id("com.bmuschko.docker-spring-boot-application")
    id("com.bmuschko.docker-remote-api")
    id("org.springframework.boot") apply false
    id("org.jetbrains.kotlin.jvm")
}

val gradleVersionProperty: String by project
val javaVersion: String by project
val springBootVersion: String by project
val springCloudStarterParentBomVersion: String by project

if (JavaVersion.current() != JavaVersion.VERSION_11) {
    throw GradleException("This build must be run with java 11")
} else {
    println("Building source with java version " + JavaVersion.current())
}

tasks.withType<Wrapper> {
    gradleVersion = gradleVersionProperty
    distributionType = Wrapper.DistributionType.ALL
}

allprojects {

    group = "org.bmuschko.gradle.docker.test"
    version = "$version"

    // Repos used in dependencyManagement section
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
    }
}

subprojects {

    // If the sub-project is a Spring Boot application
    if (File("${project.projectDir}/src/main/resources/bootstrap.yml").exists()
        || File("${project.projectDir}/src/main/resources/application.yml").exists()) {
        apply {
            plugin("org.springframework.boot")
            plugin("com.bmuschko.docker-spring-boot-application")
        }

        docker {
            springBootApplication {
                baseImage.set("openjdk:11")
                images.set(setOf("${group}/${name}:${version}", "${group}/${name}:latest"))
            }
        }
    }

    apply {
        plugin("jacoco")
        plugin("java-library")

        if (File("${project.projectDir}/src/main/kotlin").exists()) {
            plugin("org.jetbrains.kotlin.jvm")
        }

        if (File("${project.projectDir}/src/main/resources/bootstrap.yml").exists()
            || File("${project.projectDir}/src/main/resources/application.yml").exists()) {

            plugin("org.springframework.boot")
            plugin("com.bmuschko.docker-spring-boot-application")
        }
    }

    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    dependencies {

        api(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        api(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:$springCloudStarterParentBomVersion"))

        implementation("org.springframework.boot:spring-boot-starter-actuator")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        if (File("${project.projectDir}/src/main/kotlin").exists()) {
            implementation(kotlin("stdlib-jdk8"))
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "$javaVersion"
        }
    }
}
