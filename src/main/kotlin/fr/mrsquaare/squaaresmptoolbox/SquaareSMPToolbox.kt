package fr.mrsquaare.squaaresmptoolbox

import fr.mrsquaare.squaaresmptoolbox.commands.HomeCommand
import fr.mrsquaare.squaaresmptoolbox.commands.TpaCommand
import fr.mrsquaare.squaaresmptoolbox.managers.TpaManager
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import org.slf4j.LoggerFactory

object SquaareSMPToolbox : ModInitializer {
    private val logger = LoggerFactory.getLogger("squaare-smp-toolbox")

    override fun onInitialize() {
        logger.info("Initializing Squaare SMP Toolbox")

        ServerLifecycleEvents.SERVER_STOPPING.register {
            TpaManager.clearRequests()
        }

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            HomeCommand.register(dispatcher)
            TpaCommand.register(dispatcher)
        }

        logger.info("Squaare SMP Toolbox initialized")
    }
}
