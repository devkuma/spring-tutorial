plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"

    // gRPC
    id("com.google.protobuf") version "0.9.5"
}

group = "com.devkuma"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring gRPC"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"

    // gRPC
    id("com.google.protobuf") version "0.9.5"
}

group = "com.devkuma"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring gRPC"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // gRPC + protobuf
    implementation("io.grpc:grpc-kotlin-stub:1.4.3")
    implementation("io.grpc:grpc-protobuf:1.75.0")
    implementation("io.grpc:grpc-stub:1.75.0")
    implementation("io.grpc:grpc-netty-shaded:1.75.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.22.3")

    // Spring gRPC Starter
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.4"
    }

    plugins {
        register("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.75.0"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // gRPC + protobuf
    implementation("io.grpc:grpc-kotlin-stub:1.4.3")
    implementation("io.grpc:grpc-protobuf:1.75.0")
    implementation("io.grpc:grpc-stub:1.75.0")
    implementation("io.grpc:grpc-netty-shaded:1.75.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.22.3")

    // Spring gRPC Starter
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.4"
    }

    plugins {
        register("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.75.0"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
