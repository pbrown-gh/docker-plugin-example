plugins {
    id("org.springframework.boot")
    id("com.bmuschko.docker-spring-boot-application")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:11-jre-slim")
        ports.set(listOf(8080, 8080))
        images.set(setOf("com.dockerplugin.example/simple-actuator-service-2:${version}", "com.dockerplugin.example/simple-actuator-service-2:latest"))
    }
}