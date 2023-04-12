tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":copang-order-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    testImplementation(project(":tests"))
}
