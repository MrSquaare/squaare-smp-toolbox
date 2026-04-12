package fr.mrsquaare.squaaresmptoolbox.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import fr.mrsquaare.squaaresmptoolbox.events.PlayerDestroyBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
  @WrapMethod(method = "destroyBlock")
  private boolean onDestroyBlock(BlockPos pos, Operation<Boolean> original) {
    ServerPlayerGameMode gameMode = (ServerPlayerGameMode) (Object) this;

    PlayerDestroyBlockCallback.Companion.getBEFORE().invoker().onDestroyBlock(gameMode, pos);

    try {
      return original.call(pos);
    } finally {
      PlayerDestroyBlockCallback.Companion.getAFTER().invoker().onDestroyBlock(gameMode, pos);
    }
  }
}
