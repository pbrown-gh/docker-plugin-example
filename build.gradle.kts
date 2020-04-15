buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    base
    java
    id("com.bmuschko.docker-spring-boot-application") version "6.1.2"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

val gradleVersionProperty: String by project
val springBootVersion: String by project

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "$gradleVersionProperty"
    }
}

repositories {
    mavenLocal()
    gradlePluginPortal()
}

allprojects {
    apply {
        plugin("io.spring.dependency-management")
    }

    group = "com.dockerplugin.example"
    version = "$version"

    repositories {
        mavenLocal()
        gradlePluginPortal()        
    }

    dependencyManagement {
        dependencies {
            dependency("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
            dependency("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
        }
    }
}

if (JavaVersion.current() != JavaVersion.VERSION_11) {
    throw GradleException("This build must be run with JDK 11")
} else {
    println("Building source with JDK " + JavaVersion.current())
}
