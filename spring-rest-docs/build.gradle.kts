import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.devkuma"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

//extra["snippetsDir"] = file("build/generated-snippets")
val snippetsDir by extra { file("build/generated-snippets") } // 변수 변경

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

	asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor") // 1
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(snippetsDir)
}

tasks.asciidoctor {
	inputs.dir(snippetsDir)
	//dependsOn(test)
	dependsOn(tasks.test) // 변경

	doFirst { // 2
		delete {
			file("src/main/resources/static/docs")
		}
	}
}

tasks.register("copyHTML", Copy::class) { // 3
	dependsOn(tasks.asciidoctor)
	from(file("build/asciidoc/html5"))
	into(file("src/main/resources/static/docs"))
}

tasks.build { // 4
	dependsOn(tasks.getByName("copyHTML"))
}

tasks.bootJar { // 5
	dependsOn(tasks.asciidoctor)
	dependsOn(tasks.getByName("copyHTML"))
}