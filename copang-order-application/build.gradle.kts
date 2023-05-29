tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    api(project(":copang-order-domain"))
    implementation(project(":copang-order-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter")

    testImplementation(testFixtures(project(":copang-order-domain")))
}
