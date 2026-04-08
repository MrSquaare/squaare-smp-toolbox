package fr.mrsquaare.squaaresmptoolbox.models

import java.util.UUID

data class TpaRequest(
    val requesterUuid: UUID,
    val requesterName: String,
    val targetUuid: UUID,
    val targetName: String,
    val createdAtMillis: Long,
)
