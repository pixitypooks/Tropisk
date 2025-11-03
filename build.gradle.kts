plugins {
    id 'java'
    id 'com.github.johnrengelman.shadow' version '8.1.1'
}

group = 'net.tropixmc.tropisk'
version = '1.0.0'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/' }
    maven { url = 'https://repo.skriptlang.org/releases' }
}

dependencies {
    compileOnly 'org.spigotmc:spigot-api:1.21.10-R0.1-SNAPSHOT'
    compileOnly 'ch.njol:skript:2.13.1'
}

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}

shadowJar {
    archiveBaseName.set(project.name)
    archiveClassifier.set('')
    archiveVersion.set(project.version)
}

build {
    dependsOn shadowJar
}

// Release task
task release(type: Exec) {
    dependsOn shadowJar
    
    def version = project.findProperty('version') ?: '1.0.0'
    def description = project.findProperty('description') ?: 'Release ' + version
    def prerelease = project.findProperty('prerelease') ?: 'false'
    
    def token = System.getenv('GITHUB_TOKEN')
    def repo = System.getenv('GITHUB_REPOSITORY')
    
    if (!token || !repo) {
        throw new GradleException('GITHUB_TOKEN and GITHUB_REPOSITORY environment variables must be set')
    }
    
    def releaseData = [
        tag_name: version,
        name: version,
        body: new File(description).text,
        draft: false,
        prerelease: prerelease == 'true'
    ]
    
    def releaseJson = groovy.json.JsonOutput.toJson(releaseData)
    
    def createRelease = "curl -X POST -H 'Authorization: token $token' -H 'Content-Type: application/json' -d '$releaseJson' https://api.github.com/repos/$repo/releases"
    def releaseId = exec {
        commandLine 'bash', '-c', "$createRelease | jq -r '.id'"
    }.standardOutput.asText.trim()
    
    def jarFile = "build/libs/$project.name-$version.jar"
    def uploadUrl = "https://uploads.github.com/repos/$repo/releases/$releaseId/assets?name=$project.name-$version.jar"
    
    exec {
        commandLine 'bash', '-c', "curl -X POST -H 'Authorization: token $token' -H 'Content-Type: application/java-archive' --data-binary @$jarFile $uploadUrl"
    }
}