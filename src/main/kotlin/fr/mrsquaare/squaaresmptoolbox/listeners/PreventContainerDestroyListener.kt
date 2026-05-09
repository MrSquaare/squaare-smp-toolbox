package fr.mrsquaare.squaaresmptoolbox.listeners

import fr.mrsquaare.squaaresmptoolbox.events.CommandPerformCallback
import fr.mrsquaare.squaaresmptoolbox.events.ExplosionDestroyBlockCallback
import fr.mrsquaare.squaaresmptoolbox.events.LevelSetBlockCallback
import fr.mrsquaare.squaaresmptoolbox.events.PlayerDestroyBlockCallback
import fr.mrsquaare.squaaresmptoolbox.managers.ConfigManager
import fr.mrsquaare.squaaresmptoolbox.utils.BlockUtils
import java.util.ArrayDeque

object PreventContainerDestroyListener {
    private enum class TrackedOrigin {
        COMMAND,
        PLAYER_BREAK,
    }

    private object TrackedScope {
        private val origins = ThreadLocal.withInitial { ArrayDeque<TrackedOrigin>() }

        fun push(origin: TrackedOrigin) {
            origins.get().addLast(origin)
        }

        fun pop() {
            val stack = origins.get()

            if (stack.isEmpty()) {
                return
            }

            stack.removeLast()

            if (stack.isEmpty()) {
                origins.remove()
            }
        }

        fun isTrusted(): Boolean = origins.get().peekLast() != null
    }

    fun register() {
        ExplosionDestroyBlockCallback.EVENT.register { _, level, pos ->
            val config = ConfigManager.config.containerProtection

            if (!config.enabled || !config.fromExplosion) {
                return@register true
            }

            !BlockUtils.isContainer(level, pos)
        }

        CommandPerformCallback.BEFORE.register { _, _ ->
            TrackedScope.push(TrackedOrigin.COMMAND)
        }

        CommandPerformCallback.AFTER.register { _, _ ->
            TrackedScope.pop()
        }

        PlayerDestroyBlockCallback.BEFORE.register { _, _ ->
            TrackedScope.push(TrackedOrigin.PLAYER_BREAK)
        }

        PlayerDestroyBlockCallback.AFTER.register { _, _ ->
            TrackedScope.pop()
        }

        LevelSetBlockCallback.EVENT.register { level, pos, newState ->
            val config = ConfigManager.config.containerProtection

            if (!config.enabled || !config.fromEntity) {
                return@register true
            }

            level.isClientSide || !newState.isAir || !BlockUtils.isContainer(level, pos) ||
                TrackedScope.isTrusted()
        }
    }
}
