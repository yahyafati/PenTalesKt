package settings

import util.*

class SettingsData(
    var minimumRating: Int = 2000,
    var sleepInterval: Int = 60,
    var sleepLength: Int = 60,
    var startFrom: Int = 0,
    var endAt: Int = 100
) : SerializableTemplate("settings.json") {

    override fun load() {
    }
}