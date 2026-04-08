package fr.mrsquaare.squaaresmptoolbox.models

import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

data class HomeLocation(
    val dimension: ResourceKey<Level>,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
) {
    companion object {
        fun fromPlayer(player: ServerPlayer): HomeLocation =
            HomeLocation(
                dimension = player.level().dimension(),
                x = player.x,
                y = player.y,
                z = player.z,
                yaw = player.yRot,
                pitch = player.xRot,
            )
    }
}
