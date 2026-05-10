package fr.mrsquaare.squaaresmptoolbox.managers

import fr.mrsquaare.squaaresmptoolbox.SquaareSMPToolbox
import fr.mrsquaare.squaaresmptoolbox.models.Config
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.io.IOException

object ConfigManager {
    private val json =
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

    private val configFile: File =
        FabricLoader
            .getInstance()
            .configDir
            .resolve("${SquaareSMPToolbox.MOD_ID}.json")
            .toFile()

    var config: Config = Config()
        private set

    fun load() {
        if (!configFile.exists()) {
            save()

            return
        }

        try {
            val content = configFile.readText()

            config = json.decodeFromString<Config>(content)
        } catch (
            @Suppress("TooGenericExceptionCaught") e: Exception,
        ) {
            SquaareSMPToolbox.logger.error("Failed to load configuration file, using defaults.", e)

            config = Config()
        }
    }

    fun save() {
        try {
            val content = json.encodeToString(config)

            configFile.writeText(content)
        } catch (
            @Suppress("TooGenericExceptionCaught") e: Exception,
        ) {
            SquaareSMPToolbox.logger.error("Failed to save configuration file.", e)
        }
    }

    fun reload() {
        load()
    }
}
