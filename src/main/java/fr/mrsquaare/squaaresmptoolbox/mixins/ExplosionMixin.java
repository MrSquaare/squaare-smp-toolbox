package fr.mrsquaare.squaaresmptoolbox.mixins;

import fr.mrsquaare.squaaresmptoolbox.events.ExplosionDestroyBlockCallback;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
  @Shadow private Level level;

  @Shadow private ObjectArrayList<BlockPos> toBlow;

  @Inject(method = "finalizeExplosion(Z)V", at = @At("HEAD"))
  private void onFinalizeExplosion(boolean spawnParticles, CallbackInfo ci) {
    Explosion explosion = (Explosion) (Object) this;

    toBlow.removeIf(
        pos ->
            !ExplosionDestroyBlockCallback.Companion.getEVENT()
                .invoker()
                .allowDestroyBlock(explosion, level, pos));
  }
}
