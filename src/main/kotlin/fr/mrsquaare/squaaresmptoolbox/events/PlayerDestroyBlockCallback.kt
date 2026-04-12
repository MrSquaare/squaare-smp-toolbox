package fr.mrsquaare.squaaresmptoolbox.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayerGameMode

fun interface PlayerDestroyBlockCallback {
    fun onDestroyBlock(
        gameMode: ServerPlayerGameMode,
        pos: BlockPos,
    )

    companion object {
        val BEFORE: Event<PlayerDestroyBlockCallback> =
            EventFactory.createArrayBacked(
                PlayerDestroyBlockCallback::class.java,
            ) { callbacks ->
                PlayerDestroyBlockCallback { gameMode, pos ->
                    callbacks.forEach { it.onDestroyBlock(gameMode, pos) }
                }
            }

        val AFTER: Event<PlayerDestroyBlockCallback> =
            EventFactory.createArrayBacked(
                PlayerDestroyBlockCallback::class.java,
            ) { callbacks ->
                PlayerDestroyBlockCallback { gameMode, pos ->
                    callbacks.forEach { it.onDestroyBlock(gameMode, pos) }
                }
            }
    }
}
