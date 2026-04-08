package fr.mrsquaare.squaaresmptoolbox.managers

import fr.mrsquaare.squaaresmptoolbox.models.HomeLocation
import fr.mrsquaare.squaaresmptoolbox.storage.HomeSavedData
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.UUID

object HomeManager {
    private const val HOME_DATA_ID = "squaare-smp-toolbox_home_data"

    fun setHome(
        server: MinecraftServer,
        player: ServerPlayer,
    ) {
        getHomeData(server).setHome(player.uuid, HomeLocation.fromPlayer(player))
    }

    fun getHome(
        server: MinecraftServer,
        playerUuid: UUID,
    ): HomeLocation? = getHomeData(server).getHome(playerUuid)

    private fun getHomeData(server: MinecraftServer): HomeSavedData =
        server.overworld().dataStorage.computeIfAbsent(
            HomeSavedData::load,
            HomeSavedData::createNew,
            HOME_DATA_ID,
        )
}
