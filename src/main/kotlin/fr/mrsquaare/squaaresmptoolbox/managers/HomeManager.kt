package fr.mrsquaare.squaaresmptoolbox.managers

import fr.mrsquaare.squaaresmptoolbox.models.HomeLocation
import fr.mrsquaare.squaaresmptoolbox.storage.HomeSavedData
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.UUID

object HomeManager {
    fun setHome(
        server: MinecraftServer,
        player: ServerPlayer,
    ) {
        HomeSavedData.get(server).setHome(player.uuid, HomeLocation.fromPlayer(player))
    }

    fun getHome(
        server: MinecraftServer,
        playerUuid: UUID,
    ): HomeLocation? = HomeSavedData.get(server).getHome(playerUuid)
}
