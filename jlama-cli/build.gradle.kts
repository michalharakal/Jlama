// build.gradle.kts for jlama-cli

plugins {
    // Apply the Java plugin for Java project support
    java
    // Apply the Shadow plugin to create a shaded (fat) JAR, similar to maven-shade-plugin
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

// Repositories where dependencies will be resolved
repositories {
    mavenCentral()
}

// Dependencies section
dependencies {
    // Logback dependencies (replace version with actual value from parent if available)
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")

    // External library dependencies with specific versions
    implementation("info.picocli:picocli:4.7.5")
    implementation("me.tongfei:progressbar:0.10.0")
    implementation("com.diogonunes:JColor:5.5.1")

    // Project dependencies (sibling modules in the multi-module project)
    implementation(project(":jlama-core"))
//    implementation(project(":jlama-net"))
 //   implementation(project(":jlama-native")) // Adjust based on how jlama-native JAR is produced
}

// Configure the JAR task to set the Main-Class in the manifest
tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.github.tjake.jlama.cli.JlamaCli"
    }
}

// Configure resource processing to copy webapp files to the static directory
tasks.processResources {
    from("src/main/webapp/") {
        into("static")
    }
}

// Configure the shadowJar task to create a shaded JAR
tasks.shadowJar {
    // Set the output JAR file name
    archiveFileName.set("jlama-cli.jar")

    // Merge service files, equivalent to ServicesResourceTransformer
    mergeServiceFiles()

    // Configure the manifest with Main-Class and Multi-Release attributes
    manifest {
        attributes["Main-Class"] = "com.github.tjake.jlama.cli.JlamaCli"
        attributes["Multi-Release"] = "true"
    }

    // Exclude signature files from the shaded JAR
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")

    // Note: Including jlama-native JAR requires additional configuration in jlama-native module
}