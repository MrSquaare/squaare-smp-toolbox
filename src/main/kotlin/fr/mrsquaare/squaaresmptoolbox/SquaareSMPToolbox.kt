package fr.mrsquaare.squaaresmptoolbox

import fr.mrsquaare.squaaresmptoolbox.commands.HelloCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.slf4j.LoggerFactory

object SquaareSMPToolbox : ModInitializer {
    private val logger = LoggerFactory.getLogger("squaare-smp-toolbox")

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("Hello Fabric world!")

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            HelloCommand.register(dispatcher)
        }
    }
}
