package fr.mrsquaare.squaaresmptoolbox.utils

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.Container
import net.minecraft.world.level.Level

object BlockUtils {
    fun isContainer(
        level: Level,
        pos: BlockPos,
    ): Boolean {
        val blockEntity = level.getBlockEntity(pos)

        return when {
            blockEntity is Container -> true
            Direction.entries.any { side -> ItemStorage.SIDED.find(level, pos, side) != null } -> true
            ItemStorage.SIDED.find(level, pos, null) != null -> true
            else -> false
        }
    }
}
