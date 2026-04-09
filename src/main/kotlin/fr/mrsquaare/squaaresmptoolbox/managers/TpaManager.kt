package fr.mrsquaare.squaaresmptoolbox.managers

import fr.mrsquaare.squaaresmptoolbox.models.TpaRequest
import net.minecraft.server.level.ServerPlayer
import java.util.UUID

object TpaManager {
    private const val REQUEST_TIMEOUT_MILLIS = 120_000L

    private val requests = mutableMapOf<UUID, TpaRequest>()

    fun createRequest(
        requester: ServerPlayer,
        target: ServerPlayer,
    ): TpaRequest {
        val request =
            TpaRequest(
                requesterUuid = requester.uuid,
                requesterName = requester.scoreboardName,
                targetUuid = target.uuid,
                targetName = target.scoreboardName,
                createdAtMillis = System.currentTimeMillis(),
            )

        requests[target.uuid] = request

        return request
    }

    fun getRequest(targetUuid: UUID): TpaRequest? {
        val request = requests[targetUuid] ?: return null

        if (!isExpired(request)) {
            return request
        }

        requests.remove(targetUuid)

        return null
    }

    fun clearRequest(targetUuid: UUID) {
        requests.remove(targetUuid)
    }

    private fun isExpired(request: TpaRequest): Boolean =
        System.currentTimeMillis() - request.createdAtMillis >
            REQUEST_TIMEOUT_MILLIS
}
