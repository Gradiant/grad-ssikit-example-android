# Android SSI Kit Example App - Reviewing

This repository demonstrates how to integrate the Android SSI Kit Project inside an Android Application.

- Android Ported Version of the ssikit: https://github.com/Gradiant/grad-ssikit-android
- Android Ported Version of the servicematrix: https://github.com/Gradiant/grad-servicematrix-android
- Android Ported Version of the ssikit vclib: https://github.com/Gradiant/grad-ssikit-vclib-android

- Original Walt.ID SSI Kit: https://github.com/walt-id/waltid-ssikit 

### Setup

Build (Gradle) and run in **Android Studio**

## Android Application Requirements

1. Place the jars of waltid-ssikit, waltid-vclib, waltid-servicematrix in app/libs. Add those libraries to the project with the following line "implementation fileTree(include: ['*.jar'], dir: './libs')" inside the build.gradle of the project.

2. Android cannot resolve kotlin-reflect.kclasses, so this dependency line must be placed in build.gradle: "implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")".

3. Android cannot resolve mu.KotlinLogging, so this dependency line must be placed in build.gradle: "implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")".

4. Android cannot resolve ktor-client, so this two dependency lines must be placed in build.gradle: "implementation("io.ktor:ktor-client-cio:1.6.3")" and "implementation("io.ktor:ktor-client-serialization:1.6.3")".

5. Android cannot resolve TinkConfig, so this dependency line must be placed in build.gradle: "api("com.google.crypto.tink:tink:1.6.1")".

6. WaltIdServices cannot access relative Android Data Directory Path. To solve this, it is needed to set this android path in some variable, so the next line must be placed in the MainActivity: "id.walt.common.androidDataDir = dataDir.absolutePath" (Kotlin)

7. Android cannot resolve hoplite, so this 3 dependency lines must be placed in build.gradle: "implementation("com.sksamuel.hoplite:hoplite-core:1.4.7")", "implementation("com.sksamuel.hoplite:hoplite-yaml:1.4.7")" and "implementation("com.sksamuel.hoplite:hoplite-hikaricp:1.4.7")".

8. Android cannot resolve sqldroid, so this dependency line must be placed in build.gradle: "implementation('org.sqldroid:sqldroid:1.0.3')".

9. Android cannot resolve "com.nimbusds.jose", so this dependency line must be placed in build.gradle: "implementation 'com.nimbusds:nimbus-jose-jwt:9.15.1'"

10. Android cannot resolve some libraries located in alternative repositories, so this lines must be added to the list of maven repositories: 
* maven {
    url "https://maven.walt.id/repository/danubetech"
}
* maven {
    url "https://repo.danubetech.com/repository/maven-public/"
}
* maven {
    url "https://jitpack.io"
}

11. Android cannot resolve keyFormats, so this dependency line must be placed in build.gradle:
* api("info.weboftrust:ld-signatures-java:0.5-SNAPSHOT") {
        exclude group:"net.jcip", module:"jcip-annotations"
    }
A duplicity issue appears after adding this library, so the module "jcip-annotations" must be excluded.

12. Android cannot resolve klaxon, so this dependency line must be placed in build.gradle: "implementation("com.beust:klaxon:5.5")".

13. Android needs a servicematrix.properties file placed in resources/raw. It contains the matrix of services to initialize, and must not contain conf files for the services. This resource should be obtained using the function "openRawResource" and the passed to the ServiceMatrix. To be able to initialize successfully the services, two functions need to be executed before the ServiceMatrix execution: "setAndroidDatDir" and "setDataRoot".

14. A modified version of jsonld-common-java and titanium-json-ld libraries need to be used, in order to substitute the java.net.http.httpclient used in them for a okhttp3client. This libraries will be placed in the libs folder aswell. In order to use the modified version and not the one implemented in gradle, it is needed to add this line: "exclude group: "decentralized-identity", module: "jsonld-common-java"" to the dependency line: "api("info.weboftrust:ld-signatures-java:0.5-SNAPSHOT")".
* Just to clarify: Before doing this solution, the local import of the java.net.http.httpclient from jdk 11 was tried without success. It solved the dependency issue of the httpclient, but one new issue appeared: "No static method getInteger(String) in NetProperties".

15. Android cannot resolve the secp256k1-kmp-jni-android, so this dependency line must be placed in build.gradle: "implementation("fr.acinq.secp256k1:secp256k1-kmp-jni-android:0.6.0")".

16. Android cannot resolve LazySodiumAndroid, so this dependency line must be placed in build.gradle: "implementation("com.goterl:lazysodium-android:5.0.2")".
* It is also needed to provide the libjnidispatch.so to the Android application, or an "Unsatisfied Link" error will raise. This file must be placed inside src/main/jniLibs/ARCHITECTURE/libjnidispatch.so. A good practice, to be able to run this app in any smartphone, is placing the libjnidispatch.so version of the most used architectures in a directory tree like
- jniLibs -> x86 -> libjnidispatch.so
-	  -> x86_64 -> libjnidispatch.so
-	  -> arm64-v8a -> libjnidispatch.so
-	  -> armeabi-v7a -> libjnidispatch.so

17. Android cannot resolve jacksond-datatype-jsr310, so this dependency line must be placed in build.gradle: "implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.0")"

18. Android cannot resolve json-kotlin-schema, so this dependency line must be placed in build.gradle: "implementation("net.pwall.json:json-kotlin-schema:0.29")"

## Things to keep in mind

- Not all the functions of the original WaltId SSI Kit library were tested, just some of them. This means that is very likely that there are some functionalities that need some changes to work in Android. 

- This library will be continuously updated with the upcoming changes of the WaltId SSI Kit.
