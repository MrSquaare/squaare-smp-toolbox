package fr.mrsquaare.squaaresmptoolbox.models

import fr.mrsquaare.squaaresmptoolbox.utils.NbtTagType
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
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
    fun toNbt(): CompoundTag {
        val nbt = CompoundTag()

        nbt.putString("dimension", dimension.location().toString())
        nbt.putDouble("x", x)
        nbt.putDouble("y", y)
        nbt.putDouble("z", z)
        nbt.putFloat("yaw", yaw)
        nbt.putFloat("pitch", pitch)

        return nbt
    }

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

        fun fromNbt(nbt: CompoundTag): HomeLocation? {
            if (!isValidNbt(nbt)) {
                return null
            }

            val dimensionLocation =
                ResourceLocation.tryParse(nbt.getString("dimension")) ?: return null

            return HomeLocation(
                dimension = ResourceKey.create(Registries.DIMENSION, dimensionLocation),
                x = nbt.getDouble("x"),
                y = nbt.getDouble("y"),
                z = nbt.getDouble("z"),
                yaw = nbt.getFloat("yaw"),
                pitch = nbt.getFloat("pitch"),
            )
        }

        private fun isValidNbt(nbt: CompoundTag): Boolean =
            nbt.contains("dimension", NbtTagType.STRING) &&
                nbt.contains("x", NbtTagType.DOUBLE) &&
                nbt.contains("y", NbtTagType.DOUBLE) &&
                nbt.contains("z", NbtTagType.DOUBLE) &&
                nbt.contains("yaw", NbtTagType.FLOAT) &&
                nbt.contains("pitch", NbtTagType.FLOAT)
    }
}
