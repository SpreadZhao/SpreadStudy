plugins {
    id("java-gradle-plugin")
    `kotlin-dsl`
    `maven-publish`
}

group = "com.spread.st"
version = "1.0.0"

afterEvaluate {
    publishing {
        repositories {
            maven {
                url = uri("../repo")
            }
        }
    }
}


dependencies {
    implementation(gradleApi())
    implementation(libs.build.gradle)
}

gradlePlugin {
    plugins {
        create("wytsnm") {
            id = "com.spread.st"
            implementationClass = "com.spread.st.Wt"
        }
    }
}