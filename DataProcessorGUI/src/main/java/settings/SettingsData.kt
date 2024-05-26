package settings

import util.*
import java.util.logging.*

class SettingsData private constructor(
    var minimumRating: Int = 2000,
    var sleepInterval: Int = 25_000,
    var sleepDuration: Int = 3,
    var startFrom: Int = 0,
    var endAt: Int = 0,
) : ISerializable {

    @Deprecated("Use INSTANCE", ReplaceWith("SettingsData.INSTANCE"))
    constructor() : this(2000, 10000, 1, 0, 100)

    companion object {

        val FILENAME = Companion::class.java.name + ".json"
        private fun load(): SettingsData {
            val fileName = FILENAME
            LOG.info("Loading ${fileName}: $this")
            return try {
                SerializationUtil.deserializeFromFile<SettingsData>(fileName) ?: return SettingsData(
                    minimumRating = 2000,
                    sleepInterval = 10000,
                    sleepDuration = 1,
                    startFrom = 0,
                    endAt = 100,
                )
            } catch (e: Exception) {
                LOG.warning("Error loading $fileName: $e")
                SettingsData(
                    minimumRating = 2000,
                    sleepInterval = 10000,
                    sleepDuration = 1,
                    startFrom = 0,
                    endAt = 100,
                )
            }
        }

        val LOG: Logger = Logger.getLogger(SettingsData::class.java.name)
        private var INSTANCE: SettingsData? = null

        val instance: SettingsData
            get() {
                if (INSTANCE == null) {
                    INSTANCE = load()
                }
                return INSTANCE!!
            }

    }

    init {
        LOG.info("SettingsData: $this")
    }

    override val fileName: String = FILENAME

    override fun toString(): String {
        return "${this.javaClass.name}(minimumRating=$minimumRating, sleepInterval=$sleepInterval, sleepDuration=$sleepDuration, startFrom=$startFrom, endAt=$endAt)"
    }
}