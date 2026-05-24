plugins {
    id("java")
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20240303")
    implementation("org.apache.poi:poi-ooxml:5.2.5")

    // Menggunakan versi 5.3.2 untuk menghindari konflik Groovy 4
    testImplementation("io.rest-assured:rest-assured:5.3.2")
    testImplementation("org.testng:testng:7.9.0")
    testImplementation("org.apache.logging.log4j:log4j-core:2.21.1")
}

tasks.test {
    useTestNG()
}