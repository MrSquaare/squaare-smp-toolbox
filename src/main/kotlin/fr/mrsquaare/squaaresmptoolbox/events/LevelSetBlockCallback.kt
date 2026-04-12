package fr.mrsquaare.squaaresmptoolbox.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

fun interface LevelSetBlockCallback {
    fun allowSetBlock(
        level: Level,
        pos: BlockPos,
        state: BlockState,
    ): Boolean

    companion object {
        val EVENT: Event<LevelSetBlockCallback> =
            EventFactory.createArrayBacked(
                LevelSetBlockCallback::class.java,
            ) { callbacks ->
                LevelSetBlockCallback { level, pos, state ->
                    callbacks.all { it.allowSetBlock(level, pos, state) }
                }
            }
    }
}
