package fr.mrsquaare.squaaresmptoolbox

import fr.mrsquaare.squaaresmptoolbox.commands.HomeCommand
import fr.mrsquaare.squaaresmptoolbox.commands.ReloadCommand
import fr.mrsquaare.squaaresmptoolbox.commands.TpaCommand
import fr.mrsquaare.squaaresmptoolbox.listeners.PreventContainerDestroyListener
import fr.mrsquaare.squaaresmptoolbox.managers.ConfigManager
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object SquaareSMPToolbox : ModInitializer {
    const val MOD_ID = "squaare-smp-toolbox"
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        logger.info("Initializing Squaare SMP Toolbox")

        ConfigManager.load()

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            ReloadCommand.register(dispatcher)
            HomeCommand.register(dispatcher)
            TpaCommand.register(dispatcher)
        }

        PreventContainerDestroyListener.register()

        logger.info("Squaare SMP Toolbox initialized")
    }
}
