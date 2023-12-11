
repositories {
    maven("https://maven.accident.space/repository/maven-public/")
    maven("https://jitpack.io")
}

plugins {
    alias(libs.plugins.buildconfig)
    id("minecraft")
    id("publish")
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra
val modAdapter: String by extra

buildConfig {
    packageName("space.impact.$modId")
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${project.version}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("String", "MODADAPTER", "\"${modAdapter}\"")
    useKotlinOutput { topLevelConstants = true }
}

dependencies {
    api("space.impact:forgelin:2.0.+") { isChanging = true }
    api("com.github.GTNewHorizons:CodeChickenLib:1.1.+:dev") { isChanging = true }
//    implementation("space.impact:impact:1.1.0.2-8-g5772fc9.dirty")
}