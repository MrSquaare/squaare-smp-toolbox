package fr.mrsquaare.squaaresmptoolbox.listeners

import fr.mrsquaare.squaaresmptoolbox.events.ExplosionDestroyBlockCallback
import fr.mrsquaare.squaaresmptoolbox.utils.BlockUtils

object PreventContainerDestroyListener {
    fun register() {
        ExplosionDestroyBlockCallback.EVENT.register { _, level, pos ->
            !BlockUtils.isContainer(level, pos)
        }
    }
}
