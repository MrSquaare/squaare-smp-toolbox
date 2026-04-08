package fr.mrsquaare.squaaresmptoolbox.storage

import fr.mrsquaare.squaaresmptoolbox.models.HomeLocation
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.saveddata.SavedData
import java.util.UUID

class HomeSavedData : SavedData() {
    private val homes = mutableMapOf<UUID, HomeLocation>()

    fun setHome(
        playerUuid: UUID,
        home: HomeLocation,
    ) {
        homes[playerUuid] = home

        setDirty()
    }

    fun getHome(playerUuid: UUID): HomeLocation? = homes[playerUuid]

    override fun save(nbt: CompoundTag): CompoundTag {
        val homesTag = CompoundTag()

        homes.forEach { (uuid, home) ->
            val homeTag = CompoundTag()

            homeTag.putString("dimension", home.dimension.location().toString())
            homeTag.putDouble("x", home.x)
            homeTag.putDouble("y", home.y)
            homeTag.putDouble("z", home.z)
            homeTag.putFloat("yaw", home.yaw)
            homeTag.putFloat("pitch", home.pitch)
            homesTag.put(uuid.toString(), homeTag)
        }

        nbt.put("homes", homesTag)

        return nbt
    }

    companion object {
        @JvmStatic
        fun createNew(): HomeSavedData = HomeSavedData()

        @JvmStatic
        fun load(nbt: CompoundTag): HomeSavedData {
            val state = HomeSavedData()
            val homesTag = nbt.getCompound("homes")

            for (key in homesTag.allKeys) {
                val homeTag = homesTag.getCompound(key)
                val dimensionLocation =
                    ResourceLocation.tryParse(homeTag.getString("dimension"))

                if (dimensionLocation != null) {
                    val home =
                        HomeLocation(
                            dimension = ResourceKey.create(Registries.DIMENSION, dimensionLocation),
                            x = homeTag.getDouble("x"),
                            y = homeTag.getDouble("y"),
                            z = homeTag.getDouble("z"),
                            yaw = homeTag.getFloat("yaw"),
                            pitch = homeTag.getFloat("pitch"),
                        )

                    state.homes[UUID.fromString(key)] = home
                }
            }

            return state
        }
    }
}
