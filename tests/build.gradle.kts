tasks.getByName("jar") {
    enabled = true
}
dependencies {
    implementation(project(":copang-order-domain"))
}
