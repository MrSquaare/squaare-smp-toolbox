package fr.mrsquaare.squaaresmptoolbox.models

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val home: HomeConfig = HomeConfig(),
    val tpa: TpaConfig = TpaConfig(),
    val containerProtection: ContainerProtectionConfig = ContainerProtectionConfig(),
)

@Serializable
data class HomeConfig(
    val enabled: Boolean = true,
)

@Serializable
data class TpaConfig(
    val enabled: Boolean = true,
)

@Serializable
data class ContainerProtectionConfig(
    val enabled: Boolean = true,
    val fromExplosion: Boolean = true,
    val fromEntity: Boolean = true,
)
