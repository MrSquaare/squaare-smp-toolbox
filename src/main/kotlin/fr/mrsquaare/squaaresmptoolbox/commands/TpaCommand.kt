package fr.mrsquaare.squaaresmptoolbox.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import fr.mrsquaare.squaaresmptoolbox.managers.ConfigManager
import fr.mrsquaare.squaaresmptoolbox.managers.TpaManager
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component

object TpaCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands
                .literal("tpa")
                .requires { ConfigManager.config.tpa.enabled }
                .then(
                    Commands
                        .argument("user", EntityArgument.player())
                        .executes { context ->
                            requestTeleport(context)
                        },
                ).then(
                    Commands
                        .literal("a")
                        .executes { context ->
                            acceptRequest(context.source)
                        },
                ).then(
                    Commands
                        .literal("accept")
                        .executes { context ->
                            acceptRequest(context.source)
                        },
                ).then(
                    Commands
                        .literal("r")
                        .executes { context ->
                            rejectRequest(context.source)
                        },
                ).then(
                    Commands
                        .literal("reject")
                        .executes { context ->
                            rejectRequest(context.source)
                        },
                ),
        )
    }

    private fun requestTeleport(context: CommandContext<CommandSourceStack>): Int {
        val source = context.source
        val requester = source.playerOrException
        val target = EntityArgument.getPlayer(context, "user")

        if (requester.uuid == target.uuid) {
            source.sendFailure(Component.literal("You cannot send a teleport request to yourself."))

            return 0
        }

        TpaManager.createRequest(requester, target)
        target.sendSystemMessage(
            Component.literal("${requester.scoreboardName} wants to teleport to you. Use /tpa accept or /tpa reject."),
        )
        source.sendSuccess({ Component.literal("Teleport request sent to ${target.scoreboardName}.") }, false)

        return 1
    }

    private fun acceptRequest(source: CommandSourceStack): Int {
        val target = source.playerOrException
        val request = TpaManager.getRequest(target.uuid)

        if (request == null) {
            source.sendFailure(Component.literal("You do not have a pending teleport request."))

            return 0
        }

        val requester = target.server.playerList.getPlayer(request.requesterUuid)

        if (requester == null) {
            TpaManager.clearRequest(target.uuid)
            source.sendFailure(Component.literal("The requesting player is no longer online."))

            return 0
        }

        val targetLevel = target.server.getLevel(target.level().dimension())

        if (targetLevel == null) {
            source.sendFailure(Component.literal("Your current dimension is not available."))

            return 0
        }

        requester.teleportTo(targetLevel, target.x, target.y, target.z, emptySet(), target.yRot, target.xRot)
        TpaManager.clearRequest(target.uuid)
        requester.sendSystemMessage(
            Component.literal("${target.scoreboardName} accepted your teleport request."),
        )
        source.sendSuccess(
            { Component.literal("Accepted teleport request from ${requester.scoreboardName}.") },
            false,
        )

        return 1
    }

    private fun rejectRequest(source: CommandSourceStack): Int {
        val target = source.playerOrException
        val request = TpaManager.getRequest(target.uuid)

        if (request == null) {
            source.sendFailure(Component.literal("You do not have a pending teleport request."))

            return 0
        }

        val requester = target.server.playerList.getPlayer(request.requesterUuid)

        TpaManager.clearRequest(target.uuid)
        requester?.sendSystemMessage(Component.literal("${target.scoreboardName} rejected your teleport request."))
        source.sendSuccess({ Component.literal("Rejected teleport request from ${request.requesterName}.") }, false)

        return 1
    }
}
