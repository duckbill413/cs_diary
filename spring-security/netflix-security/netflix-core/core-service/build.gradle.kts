dependencies {
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-port"))

    runtimeOnly(project(":netflix-adapters:adapter-http"))

    implementation("org.springframework:spring-context")
}
