plugins {
    // Apply the Java plugin (or java-library if appropriate)
    java
}

group = "com.github.tjake"
// Define the version; you can substitute the property or literal value as needed.
version = "1.0.0"
// Optional: set the project name/description
description = "Jlama Core"


tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("--enable-preview", "--add-modules", "jdk.incubator.vector"))
}

tasks.withType<Test> {
    jvmArgs("--enable-preview", "--add-modules", "jdk.incubator.vector")
}

/*
application {
    applicationDefaultJvmArgs = listOf("--enable-preview", "--add-modules", "jdk.incubator.vector")
}

 */

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.google.guava:guava:32.0.1-jre")
    implementation("org.jctools:jctools-core:4.0.1")
    implementation("com.hubspot.jinjava:jinjava:2.7.2") {
        // Exclude the Guava transitive dependency from Jinjava
        exclude(group = "com.google.guava", module = "guava")
    }
    implementation("net.fellbaum:jemoji:1.4.1")
    implementation("net.jafama:jafama:2.3.2")
}
