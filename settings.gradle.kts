plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "PenTalesREST"
include("database_populator")
include("DataProcessorGUI")
include("ReadingRealmProjectRunner")
