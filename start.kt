#!/usr/bin/env kscript
@file:DependsOn("com.github.omarmiatello.gradle-modules-config:script:1.0.3")

import com.github.omarmiatello.gradlemoduleconfig.script.copyFromTemplate
import java.io.File

fun main() {

    // Setup

    val (owner, projectname) = System.getenv("GITHUB_REPOSITORY")?.split("/")
        ?: error("🚨 No GITHUB_REPOSITORY environment variable. Are you running from Github Actions?")
    fun File.editText(block: (String) -> String) = writeText(block(readText()))
    fun String.clean() = replace("owner", owner)
        .replace("android-template", projectname)
        .replace("start-android", projectname)
        .replace("project-name", projectname)
        .replace("projectname", projectname.filter { it.isLetterOrDigit() })

    val shouldSkip = listOf("build", ".git")
    copyFromTemplate(
        source = File("project-templates"),
        destination = File(""),
        contentReplace = mapOf(
            "owner" to owner,
            "fullname" to owner,
            "emailaddress" to owner,
            "project-name" to projectname,
            "projectname" to projectname.filter { it.isLetterOrDigit() },
        ),
        dirReplace = mapOf(),
        onEnter = { it.name !in shouldSkip && (it.name == "buildSrc" || "src" !in it.list().orEmpty()) }
    )

    // Replace content

    File("build.gradle.kts").editText { it.clean() }
    File("settings.gradle.kts").editText { it.replaceAfter("android-template\"", "\n\n").clean() }
    File("newmodule.kt").editText { it.clean() }
    File("README.md").editText { it ->
        it.replaceBefore("## Features", File(".github/template-cleanup/README.md").readText().clean())
    }

    // Remove this script
    listOf(
        ".template-cleanup/README.md",
        ".github/workflows/start.yaml",
        ".github/template-cleanup/README.md",
        "start.kt",
    ).forEach { File(it).delete() }

    println("🎉 First step: COMPLETED!")
}
