rootProject.name = "netflix-security"

include("netflix-apps:app-api")
include("netflix-apps:app-batch")

include("netflix-adapters:adapter-http")
include("netflix-adapters:adapter-persistence")
include("netflix-adapters:adapter-redis")

include("netflix-commons")

include("netflix-core:core-domain")
include("netflix-core:core-port")
include("netflix-core:core-service")
include("netflix-core:core-usecase")