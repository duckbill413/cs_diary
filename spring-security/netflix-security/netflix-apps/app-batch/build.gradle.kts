dependencies {
  implementation(project(":netflix-core:core-usecase"))
  implementation(project(":netflix-core:core-domain"))
  implementation(project(":netflix-commons"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-batch")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa") // jpa
  implementation("org.springframework:spring-tx")

  implementation("org.flywaydb:flyway-core")
  implementation("org.flywaydb:flyway-mysql")

  runtimeOnly("com.mysql:mysql-connector-j")
  runtimeOnly(project(":netflix-core:core-service"))

  runtimeOnly(project(":netflix-adapters:adapter-http"))
  runtimeOnly(project(":netflix-adapters:adapter-persistence"))
}

val appMainClassName = "wh.duckbill.netflix.NetflixBatchApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
  mainClass.set(appMainClassName)
  archiveClassifier.set("boot")
}