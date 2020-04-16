plugins {
    id("org.springframework.boot")
    id("com.bmuschko.docker-spring-boot-application")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:11-jre-slim")
        ports.set(listOf(8080, 8080))
        images.set(setOf("com.dockerplugin.example/simple-actuator-service-1:${version}", "com.dockerplugin.example/simple-actuator-service-1:latest"))
    }
}
