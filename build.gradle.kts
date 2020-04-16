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

val gradleVersion: String by project
val springBootVersion: String by project
val springCloudStarterParentBomVersion: String by project

if (JavaVersion.current() != JavaVersion.VERSION_11) {
    throw GradleException("This build must be run with JDK 11")
} else {
    println("Building source with JDK " + JavaVersion.current())
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "$gradleVersion"
    }
}

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

    dependencies {
        // Enable actuator endpoints on all services
        "implementation"("org.springframework.boot:spring-boot-starter-actuator")
    }
}
