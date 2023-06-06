tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}

dependencies {
    implementation(project(":copang-order-domain"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    testImplementation(testFixtures(project(":copang-order-domain")))
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
