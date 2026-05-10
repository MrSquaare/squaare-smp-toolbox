package fr.mrsquaare.squaaresmptoolbox.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import fr.mrsquaare.squaaresmptoolbox.events.ExplosionCallback;
import fr.mrsquaare.squaaresmptoolbox.events.ExplosionDestroyBlockCallback;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
  @Shadow private Level level;

  @Shadow private ObjectArrayList<BlockPos> toBlow;

  @WrapMethod(method = "finalizeExplosion(Z)V")
  private void onFinalizeExplosion(boolean spawnParticles, Operation<Void> original) {
    Explosion explosion = (Explosion) (Object) this;

    toBlow.removeIf(
        pos ->
            !ExplosionDestroyBlockCallback.Companion.getEVENT()
                .invoker()
                .allowDestroyBlock(explosion, level, pos));

    ExplosionCallback.Companion.getBEFORE().invoker().onExplosion(explosion);

    try {
      original.call(spawnParticles);
    } finally {
      ExplosionCallback.Companion.getAFTER().invoker().onExplosion(explosion);
    }
  }
}
