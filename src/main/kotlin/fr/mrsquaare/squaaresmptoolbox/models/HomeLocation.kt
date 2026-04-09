package fr.mrsquaare.squaaresmptoolbox.models

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
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
    fun toNbt(): CompoundTag =
        CODEC
            .encodeStart(NbtOps.INSTANCE, this)
            .result()
            .orElseThrow() as CompoundTag

    companion object {
        private val CODEC: Codec<HomeLocation> =
            RecordCodecBuilder.create { instance ->
                instance
                    .group(
                        ResourceLocation.CODEC
                            .xmap(
                                { ResourceKey.create(Registries.DIMENSION, it) },
                                { it.location() },
                            ).fieldOf("dimension")
                            .forGetter(HomeLocation::dimension),
                        Codec.DOUBLE.fieldOf("x").forGetter(HomeLocation::x),
                        Codec.DOUBLE.fieldOf("y").forGetter(HomeLocation::y),
                        Codec.DOUBLE.fieldOf("z").forGetter(HomeLocation::z),
                        Codec.FLOAT.fieldOf("yaw").forGetter(HomeLocation::yaw),
                        Codec.FLOAT.fieldOf("pitch").forGetter(HomeLocation::pitch),
                    ).apply(instance, ::HomeLocation)
            }

        fun fromPlayer(player: ServerPlayer): HomeLocation =
            HomeLocation(
                dimension = player.level().dimension(),
                x = player.x,
                y = player.y,
                z = player.z,
                yaw = player.yRot,
                pitch = player.xRot,
            )

        fun fromNbt(nbt: CompoundTag): HomeLocation? =
            CODEC
                .parse(NbtOps.INSTANCE, nbt)
                .result()
                .orElse(null)
    }
}
