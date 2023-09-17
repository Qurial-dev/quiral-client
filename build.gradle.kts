plugins {
	id("fabric-loom") version "1.3-SNAPSHOT"
}

group = "com.quiral.client"
version = "0.0.1"

dependencies {
	minecraft("com.mojang:minecraft:${property("minecraft_version")}")
	mappings(loom.officialMojangMappings())
	modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
}

java {
	withSourcesJar()
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

loom {
	accessWidenerPath.set(file("src/main/resources/quiral.accesswidener"))
}

tasks {
	processResources {
		inputs.property("version", project.version)
		filesMatching("fabric.mod.json") {
			expand("version" to project.version)
		}
	}

	withType<JavaCompile> {
		options.encoding = Charsets.UTF_8.name()
		options.release.set(17)
	}

	withType<AbstractArchiveTask> {
		archiveBaseName.set("quiral")
	}

	jar {
		from("LICENSE") { rename { return@rename "${it}_${rootProject.name}" } }
	}
}