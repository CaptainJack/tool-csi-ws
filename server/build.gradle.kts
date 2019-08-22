plugins {
	kotlin("multiplatform")
	id("ru.capjack.bintray")
}

kotlin {
	jvm()
	
	sourceSets {
		get("commonMain").dependencies {
			implementation(kotlin("stdlib-common"))
			implementation(project(":csi-transport-common"))
			implementation("ru.capjack.csi:csi-core-server")
			implementation("ru.capjack.tool:tool-utils")
			implementation("ru.capjack.tool:tool-logging")
		}
		
		get("jvmMain").dependencies {
			implementation(kotlin("stdlib-jdk8"))
			implementation("io.netty:netty-transport")
			implementation("io.netty:netty-transport-native-epoll")
			implementation("io.netty:netty-codec-http")
		}
	}
}
