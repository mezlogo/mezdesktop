plugins {
    id("mezlogo.common-build")
    application
}

dependencies {
    implementation(project(":core"))
    implementation("info.picocli:picocli:4.6.3")
}

application {
    mainClass.set("mezlogo.jknife.cli.MainCommand")
    applicationName = "jknife"
}

val generateAutocomplete by tasks.registering(JavaExec::class) {
    val completionsDir = layout.buildDirectory.dir("completions")
    outputs.dir(completionsDir)
    args = listOf(
        "-f",
        "-o",
        completionsDir.get().file("jknife_completion").asFile.absolutePath,
        "mezlogo.jknife.cli.MainCommand"
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
