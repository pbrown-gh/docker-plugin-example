dependencies {
    implementation("de.codecentric:spring-boot-admin-starter-server:2.1.6")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // For config access
    implementation("org.springframework.cloud:spring-cloud-config-client")
    // For eureka discovery
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    // Allows JMX service management
    implementation("org.jolokia:jolokia-core")
}
