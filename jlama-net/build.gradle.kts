plugins {
    java
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.protobuf") version "0.9.4"
    id("org.openapi.generator") version "7.7.0"

}

val springBootVersion = "3.3.2"
val jerseyVersion = "3.0.4"
val springDocVersion = "1.6.14"

val swagger_annotations_version = "1.6.5"
val jackson_version = "2.17.1"
val jackson_databind_version = "2.17.1"
val jakarta_annotation_version = "2.1.0"
val bean_validation_version = "3.0.2"
val jersey_version = "3.0.4"
val junit_version = "5.8.2"





sourceSets["main"].java.srcDir("$buildDir/generated")


dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    implementation(project(":jlama-core"))
    runtimeOnly("io.grpc:grpc-netty-shaded:1.59.0")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    implementation("io.grpc:grpc-protobuf:1.59.0")
    implementation("io.grpc:grpc-stub:1.59.0")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    //implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.jctools:jctools-core:4.0.1")
    // Jersey dependencies
    val jerseyVersion = "3.1.10"
    implementation("org.glassfish.jersey.core:jersey-client:3.1.10")
    implementation("org.glassfish.jersey.connectors:jersey-apache-connector:$jerseyVersion")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jerseyVersion")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:$jerseyVersion")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:$jerseyVersion")

    // Jakarta dependencies
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // Bean validation dependencies
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.hibernate.validator:hibernate-validator-annotation-processor:8.0.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Jackson dependency
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.junit.jupiter", module = "junit-jupiter-engine")
    }
    testImplementation("io.grpc:grpc-testing:1.59.0")
    testImplementation("io.github.stefanbratanov:jvm-openai:0.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    implementation("io.swagger:swagger-annotations:$swagger_annotations_version")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("org.glassfish.jersey.core:jersey-client:$jersey_version")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jersey_version")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:$jersey_version")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:$jersey_version")
    implementation("org.glassfish.jersey.connectors:jersey-apache-connector:$jersey_version")
    implementation("com.fasterxml.jackson.core:jackson-core:$jackson_version")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jackson_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_databind_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakarta_annotation_version")
    implementation("jakarta.validation:jakarta.validation-api:$bean_validation_version")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")


}

val protobufVersion by extra("3.17.3")
val protobufPluginVersion by extra("0.8.14")
val grpcVersion by extra("1.40.1")

protobuf {
    protoc {
        artifact = libs.protoc.asProvider().get().toString()
    }
    plugins {
        create("java") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        create("grpc") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        create("grpckt") {
            artifact = libs.protoc.gen.grpc.kotlin.get().toString() + ":jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}

// Configure OpenAPI code generation
openApiGenerate {
    generatorName.set("java")
    library.set("jersey3")
    inputSpec.set("$projectDir/openai_spec.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("com.github.tjake.jlama.net.openai.api")
    modelPackage.set("com.github.tjake.jlama.net.openai.model")
    //generateApi.set(false) // Do not generate API interfaces
    generateModelTests.set(true) // Skip model tests
    //generateSupportingFiles.set(true)
    skipValidateSpec.set(true) // Skip validation of the spec
    configOptions.set(
        mapOf(
            "useTags" to "true",
            "useBeanValidation" to "true",
            "hideGenerationTimestamp" to "true",
            "performBeanValidation" to "true",
            "useJakartaEe" to "true",
            "useOneOfDiscriminatorLookup" to "true",
            "openApiNullable" to "false",
            "useOptional" to "true",
            "containerDefaultToNull" to "true",
            "serializationLibrary" to "jackson",
            "generateAliasAsModel" to "true",
            "interfaceOnly" to "true",
            "generateBuilders" to "true",
            "skipDefaultInterface" to "true",
            "documentationProvider" to "none",
            "sourceFolder" to "java/main"
        )
    )
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("--enable-preview", "--add-modules", "jdk.incubator.vector"))
}

tasks.withType<Test> {
    jvmArgs("--enable-preview", "--add-modules", "jdk.incubator.vector")
}

