plugins {
    kotlin("jvm")
}

group = "org.pentales.projectrunner"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

sourceSets {
    main {
        java {
            srcDir("src/main/kotlin")
        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "org.pentales.projectrunner.ProjectRunnerApplicationKt"
    }

    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}