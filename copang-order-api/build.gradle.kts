tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":copang-order-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}
