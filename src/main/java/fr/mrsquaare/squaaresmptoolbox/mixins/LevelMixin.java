package fr.mrsquaare.squaaresmptoolbox.mixins;

import fr.mrsquaare.squaaresmptoolbox.events.LevelSetBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
  @Inject(
      method =
          "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
      at = @At("HEAD"),
      cancellable = true)
  private void onSetBlock(
      BlockPos pos,
      BlockState state,
      int flags,
      int recursionLeft,
      CallbackInfoReturnable<Boolean> cir) {
    Level level = (Level) (Object) this;

    if (!LevelSetBlockCallback.Companion.getEVENT().invoker().allowSetBlock(level, pos, state)) {
      cir.setReturnValue(false);
    }
  }
}
