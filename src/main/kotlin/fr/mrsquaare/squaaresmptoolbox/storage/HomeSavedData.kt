package fr.mrsquaare.squaaresmptoolbox.storage

import fr.mrsquaare.squaaresmptoolbox.models.HomeLocation
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.MinecraftServer
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
            homesTag.put(uuid.toString(), home.toNbt())
        }

        nbt.put(HOME_TAG_KEY, homesTag)

        return nbt
    }

    companion object {
        private const val HOME_DATA_ID = "squaare-smp-toolbox_home_data"
        private const val HOME_TAG_KEY = "homes"

        fun load(nbt: CompoundTag): HomeSavedData {
            val state = HomeSavedData()
            val homesTag = nbt.getCompound(HOME_TAG_KEY)

            for (key in homesTag.allKeys) {
                val uuid = runCatching { UUID.fromString(key) }.getOrNull() ?: continue
                val homeTag = homesTag.getCompound(key)
                val homeLocation = HomeLocation.fromNbt(homeTag) ?: continue

                state.homes[uuid] = homeLocation
            }

            return state
        }

        fun get(server: MinecraftServer): HomeSavedData =
            server.overworld().dataStorage.computeIfAbsent(
                ::load,
                ::HomeSavedData,
                HOME_DATA_ID,
            )
    }
}
