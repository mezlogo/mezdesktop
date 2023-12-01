plugins {
    id("mezlogo.common-build")
    application
}

dependencies {
    implementation(project(":core"))
    implementation("info.picocli:picocli:4.6.3")
    annotationProcessor("info.picocli:picocli-codegen:4.6.3")
}

tasks.withType(JavaCompile::class.java) {
    options.compilerArgs.add("-Aproject=${project.group}/${project.name}")
}

application {
    mainClass.set("mezlogo.trun.cli.MainCommand")
    applicationName = "trun"
}

val generateAutocomplete by tasks.registering(JavaExec::class) {
    val completionsDir = layout.buildDirectory.dir("completions")
    outputs.dir(completionsDir)
    args = listOf(
        "-f",
        "-o",
        completionsDir.get().file("trun_completion").asFile.absolutePath,
        "mezlogo.trun.cli.MainCommand"
    )
    mainClass.set("picocli.AutoComplete")
    classpath = sourceSets.main.get().runtimeClasspath
}

val startScripts by tasks.named("startScripts") {
    dependsOn(generateAutocomplete)
}

distributions {
    main {
        contents {
            from(generateAutocomplete) {
                into("completions")
            }
        }
    }
}
