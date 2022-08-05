# Welcome to Kode build guide

If you just want to build Kode from source, here are the instructions for a simplified process you can follow.

## Requirements

Kode is written on Java SE platform and is packaged using Install4j Multi-Platform Java Installer Builder.

- [Java JDK 17+](https://www.azul.com/downloads/?version=java-17-lts&package=jdk) (Preferably Azul Zulu Builds)
- [Install4j 9+](https://www.ej-technologies.com/download/install4j/files) (Optional)
- [Git](https://git-scm.com/downloads) (Optional)

Preferentially use [IntelliJ IDEA](https://www.jetbrains.com/idea/download) as your integrated development environment.

## Get the Source

Download or clone the project from GitHub:

```shell
$ git clone --recursive https://github.com/Kode-Devs/Kode.git
$ cd Kode
```

## For development

First you need to set some required environment variables:

- **JAVA_HOME** : Path to your Java JDK installation directory.

```shell
$ ./gradlew build
```

To remove existing build files:

```shell
$ ./gradlew clean
```

## To Generate Production bundle

First you need to set some required environment variables:

- **INSTALL4J_HOME** : Path to your Install4j installation directory.
- **INSTALL4J_LICENSE** (Optional) : License Key for your Install4j installation.

```shell
$ ./gradlew buildInstallers
```

You will find your generated installers in the `build/distributions` directory.