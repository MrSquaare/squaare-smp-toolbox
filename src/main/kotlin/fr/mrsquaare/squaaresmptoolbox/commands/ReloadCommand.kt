package fr.mrsquaare.squaaresmptoolbox.commands

import com.mojang.brigadier.CommandDispatcher
import fr.mrsquaare.squaaresmptoolbox.managers.ConfigManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

object ReloadCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands
                .literal("ssmp")
                .requires { it.hasPermission(2) }
                .then(
                    Commands
                        .literal("reload")
                        .executes { context ->
                            reload(context.source)
                        },
                ),
        )
    }

    private fun reload(source: CommandSourceStack): Int {
        ConfigManager.reload()
        source.sendSuccess({ Component.literal("Squaare SMP Toolbox configuration reloaded.") }, true)

        return 1
    }
}
