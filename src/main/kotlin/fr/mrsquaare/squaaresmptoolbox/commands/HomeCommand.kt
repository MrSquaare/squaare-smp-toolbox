package fr.mrsquaare.squaaresmptoolbox.commands

import com.mojang.brigadier.CommandDispatcher
import fr.mrsquaare.squaaresmptoolbox.managers.HomeManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

object HomeCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands
                .literal("home")
                .executes { context ->
                    teleportHome(context.source)
                }.then(
                    Commands
                        .literal("set")
                        .executes { context ->
                            setHome(context.source)
                        },
                ),
        )
    }

    private fun teleportHome(source: CommandSourceStack): Int {
        val player = source.playerOrException
        val home = HomeManager.getHome(source.server, player.uuid)

        if (home == null) {
            source.sendFailure(Component.literal("You do not have a home set."))

            return 0
        }

        val level = player.server.getLevel(home.dimension)

        if (level == null) {
            source.sendFailure(Component.literal("Your home dimension is not available."))

            return 0
        }

        player.teleportTo(level, home.x, home.y, home.z, emptySet(), home.yaw, home.pitch)
        source.sendSuccess({ Component.literal("Teleported home.") }, false)

        return 1
    }

    private fun setHome(source: CommandSourceStack): Int {
        val player = source.playerOrException

        HomeManager.setHome(source.server, player)
        source.sendSuccess({ Component.literal("Home set.") }, false)

        return 1
    }
}
