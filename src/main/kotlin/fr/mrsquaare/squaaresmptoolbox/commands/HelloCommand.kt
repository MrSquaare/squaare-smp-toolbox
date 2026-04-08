package fr.mrsquaare.squaaresmptoolbox.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

object HelloCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands
                .literal("hello")
                .executes { context ->
                    sendHello(context.source, "World")
                }.then(
                    Commands
                        .argument("name", StringArgumentType.greedyString())
                        .executes { context ->
                            val name = StringArgumentType.getString(context, "name")

                            sendHello(context.source, name)
                        },
                ),
        )
    }

    private fun sendHello(
        source: CommandSourceStack,
        name: String,
    ): Int {
        source.sendSuccess({ Component.literal("Hello, $name!") }, false)

        return 1
    }
}
