# brother-label-printer-kt

![Build](https://github.com/omarmiatello/brother-label-printer-kt/workflows/Pre%20Merge%20Checks/badge.svg)

This is your new Kotlin Android Project! Happy hacking!

## Todo list

- [x] Create a new template project.
- [ ] Edit [newmodule.kt](newmodule.kt) configuration.
    - Need help? Try it on [Kotlin Playground](https://pl.kotl.in/9oXsIoDNS) or [our samples here](https://omarmiatello.github.io/gradle-modules-config/config_online.html).
- [ ] Run workflow [Build Modules](https://github.com/omarmiatello/brother-label-printer-kt/actions/workflows/build-modules.yaml) 
- [ ] Choose a [LICENSE](https://github.com/omarmiatello/brother-label-printer-kt/community/license/new?branch=main).
- [ ] Set your `ORG_GRADLE_PROJECT_NEXUS_USERNAME`, `ORG_GRADLE_PROJECT_NEXUS_PASSWORD`, `ORG_GRADLE_PROJECT_SIGNING_KEY` and `ORG_GRADLE_PROJECT_SIGNING_PWD` secrets in [Settings](https://github.com/omarmiatello/project/settings/secrets/actions).
- [ ] Code some cool apps and libraries 🚀.

## Features

- **100% Kotlin-only template**.
- 3 Sample modules (Android app, Android library, Kotlin library).
- Sample Espresso, Instrumentation & JUnit tests.
- 100% Gradle Kotlin DSL setup.
- CI Setup with GitHub Actions.
- Publish to **Maven Central** with Github Actions.
- Dependency versions managed via `buildSrc`.
- Kotlin Static Analysis via `ktlint` and `detekt`.
- Issues Template (bug report + feature request).
- Pull Request Template.

## Gradle Setup

This template is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the [Dependencies.kt](buildSrc/src/main/java/Dependencies.kt) file in the `buildSrc` folder. This provides convenient auto-completion when writing your gradle files.

## Static Analysis

This template is using [**ktlint**](https://github.com/pinterest/ktlint) with the [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) plugin to format your code. To reformat all the source code as well as the buildscript you can run the `ktlintFormat` gradle task.

This template is also using [**detekt**](https://github.com/detekt/detekt) to analyze the source code, with the configuration that is stored in the [detekt.yml](config/detekt/detekt.yml) file (the file has been generated with the `detektGenerateConfig` task).

## Powered by GitHub Actions

This template is using [**GitHub Actions**](https://github.com/omarmiatello/android-template/actions) as CI. You don't need to setup any external service and you should have a running CI once you start using this template.

There are currently the following workflows available:
- [Build Modules](.github/workflows/build-modules.yml) - Will build gradle modules based on [newmodule.kt](newmodule.kt) configuration.
- [Validate Gradle Wrapper](.github/workflows/gradle-wrapper-validation.yml) - Will check that the gradle wrapper has a valid checksum.
- [Pre Merge Checks](.github/workflows/pre-merge.yaml) - Will run the `build`, `check` and `publishToMavenLocal` tasks.
- [Publish Snapshot](.github/workflows/publish-snapshot.yaml) - Will publish a `-SNAPSHOT` of the libraries to Sonatype.
- [Publish Release](.github/workflows/publish-release.yaml) - Will publish a new release version of the libraries to Maven Central on tag pushes.

## Publishing

The template is setup to be **ready to publish** a library/artifact on a Maven Repository.

For every module you want to publish you simply have to add the `publish` plugin:

```
plugins {
    publish
}
```

### To Maven Central

In order to use this template to publish on Maven Central, you need to configure some secrets on your repository:

| Secret name | Value |
| --- | --- | 
| `ORG_GRADLE_PROJECT_NEXUS_USERNAME` | The username you use to access Sonatype's services (such as [Nexus](https://oss.sonatype.org/) and [Jira](https://issues.sonatype.org/)) |
| `ORG_GRADLE_PROJECT_NEXUS_PASSWORD` | The password you use to access Sonatype's services (such as [Nexus](https://oss.sonatype.org/) and [Jira](https://issues.sonatype.org/)) |
| `ORG_GRADLE_PROJECT_SIGNING_KEY` | The GPG Private key to sign your artifacts. You can obtain it with `gpg --armor --export-secret-keys <your@email.here>` or you can create one key online on [pgpkeygen.com](https://pgpkeygen.com). The key starts with a `-----BEGIN PGP PRIVATE KEY BLOCK-----`. |
| `ORG_GRADLE_PROJECT_SIGNING_PWD` | The passphrase to unlock your private key (you picked it when creating the key). |

The template already sets up [Dokka](https://kotlin.github.io/dokka/) for project documentation and attaches `-sources.jar` to your publications.

Once set up, the following workflows will take care of publishing:

- [Publish Snapshot](.github/workflows/publish-snapshot.yaml) - To publish `-SNAPSHOT` versions to Sonatype. The workflow is setup to run either manually (with `workflow_dispatch`) or on every merge.
- [Publish Release](.github/workflows/publish-release.yaml) - Will publish a new release version of the libraries to Maven Central on tag pushes. You can trigger the workflow also manually if needed.

### To Jitpack

If you're using [JitPack](https://jitpack.io/), you don't need any further configuration and you can just configure the repo on JitPack.

You probably want to disable the [Publish Snapshot] and [Publish Release](.github/workflows/publish-release.yaml) workflows (delete the files), as Jitpack will take care of that for you.

## Contributing 🤝

Feel free to open a issue or submit a pull request for any bugs/improvements.
