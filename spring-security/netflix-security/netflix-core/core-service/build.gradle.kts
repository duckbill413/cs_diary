dependencies {
    implementation(project(":netflix-core:core-usecase"))
    implementation(project(":netflix-core:core-port"))

    runtimeOnly(project(":netflix-adapters:adapter-http"))
    runtimeOnly(project(":netflix-adapters:adapter-persistence"))

    implementation("org.springframework:spring-context")
}
