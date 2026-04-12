package fr.mrsquaare.squaaresmptoolbox.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.commands.CommandSourceStack

fun interface CommandPerformCallback {
    fun onCommandPerform(
        source: CommandSourceStack,
        command: String,
    )

    companion object {
        val BEFORE: Event<CommandPerformCallback> =
            EventFactory.createArrayBacked(
                CommandPerformCallback::class.java,
            ) { callbacks ->
                CommandPerformCallback { source, command ->
                    callbacks.forEach { it.onCommandPerform(source, command) }
                }
            }

        val AFTER: Event<CommandPerformCallback> =
            EventFactory.createArrayBacked(
                CommandPerformCallback::class.java,
            ) { callbacks ->
                CommandPerformCallback { source, command ->
                    callbacks.forEach { it.onCommandPerform(source, command) }
                }
            }
    }
}
