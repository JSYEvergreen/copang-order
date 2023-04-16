tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":copang-order-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("'javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate.validator:hibernate-validator:6.0.2.Final")

    testImplementation(project(":tests"))
}
