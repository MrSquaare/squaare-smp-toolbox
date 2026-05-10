package fr.mrsquaare.squaaresmptoolbox.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.level.Explosion

fun interface ExplosionCallback {
    fun onExplosion(explosion: Explosion)

    companion object {
        val BEFORE: Event<ExplosionCallback> =
            EventFactory.createArrayBacked(
                ExplosionCallback::class.java,
            ) { callbacks ->
                ExplosionCallback { explosion ->
                    callbacks.forEach { it.onExplosion(explosion) }
                }
            }

        val AFTER: Event<ExplosionCallback> =
            EventFactory.createArrayBacked(
                ExplosionCallback::class.java,
            ) { callbacks ->
                ExplosionCallback { explosion ->
                    callbacks.forEach { it.onExplosion(explosion) }
                }
            }
    }
}
