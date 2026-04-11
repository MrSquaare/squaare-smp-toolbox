package fr.mrsquaare.squaaresmptoolbox.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

fun interface ExplosionDestroyBlockCallback {
    fun allowDestroyBlock(
        explosion: Explosion,
        level: Level,
        pos: BlockPos,
    ): Boolean

    companion object {
        val EVENT: Event<ExplosionDestroyBlockCallback> =
            EventFactory.createArrayBacked(
                ExplosionDestroyBlockCallback::class.java,
            ) { callbacks ->
                ExplosionDestroyBlockCallback { explosion, level, pos ->
                    callbacks.all { it.allowDestroyBlock(explosion, level, pos) }
                }
            }
    }
}
