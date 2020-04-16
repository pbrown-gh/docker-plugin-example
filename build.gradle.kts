buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    base
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("com.bmuschko.docker-spring-boot-application") version "6.1.2"
}

val gradleVersionProperty: String by project
val springBootVersion: String by project
val springCloudStarterParentBomVersion: String by project
val javaVersion: String by project

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "$gradleVersionProperty"
    }
}

// Need to define spring repos here to get access to dependencyManagement.importedProperties in config
// phase. There must be a better way to do this without having to do again in allProjects but can't find it.
repositories {
    mavenLocal()
    jcenter()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

allprojects {
    apply {
        plugin("io.spring.dependency-management")
    }

    group = "com.dockerplugin.example"
    version = "$version"

    repositories {
        mavenLocal()
        jcenter()
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudStarterParentBomVersion")
            mavenBom("org.springframework.boot:spring-boot-starter-parent:$springBootVersion")
        }
        dependencies {
        }
    }
}

subprojects {

    apply {
        plugin("java")
    }

    // The subproject is the receiver of the body
    dependencies {

        // Enable actuator endpoints on all services
        "compile"("org.springframework.boot:spring-boot-starter-actuator")
    }
}
