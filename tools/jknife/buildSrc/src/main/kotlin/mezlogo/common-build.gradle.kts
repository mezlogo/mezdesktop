package mezlogo

plugins {
    java
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(platform("org.mockito:mockito-bom:5.3.1"))
    testImplementation("org.mockito:mockito-core")

    testImplementation("org.assertj:assertj-core:3.24.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
    jacocoTestReport {
        reports {
            xml.required.set(true)
        }
    }
}
