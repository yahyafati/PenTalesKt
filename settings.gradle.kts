rootProject.name = "PenTalesREST"
include("src:main:HelperScripts")
findProject(":src:main:HelperScripts")?.name = "HelperScripts"
include("src:database_populator")
findProject(":src:database_populator")?.name = "database_populator"
