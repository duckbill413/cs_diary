dependencies {
  implementation(project(":netflix-core:core-usecase"))
  implementation(project(":netflix-commons"))

  runtimeOnly(project(":netflix-core:core-service"))

  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.springframework.security:spring-security-oauth2-client")
  implementation("org.springframework.data:spring-data-commons")
  implementation("org.springframework.boot:spring-boot-devtools")

  implementation("org.springframework.boot:spring-boot-starter-web")
}

val appMainClassName = "wh.duckbill.netflix.NetflixApiApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
  mainClass.set(appMainClassName)
  archiveClassifier.set("boot")
}