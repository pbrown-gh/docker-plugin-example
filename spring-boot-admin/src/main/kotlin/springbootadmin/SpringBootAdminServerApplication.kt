package org.bmuschko.gradle.docker.test.springbootadmin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableAdminServer
open class SpringBootAdminServerApplication

fun main(args: Array<String>) {
    runApplication<SpringBootAdminServerApplication>(*args)
}
