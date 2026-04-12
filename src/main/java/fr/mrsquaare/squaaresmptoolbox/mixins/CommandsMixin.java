package fr.mrsquaare.squaaresmptoolbox.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.brigadier.ParseResults;
import fr.mrsquaare.squaaresmptoolbox.events.CommandPerformCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Commands.class)
public abstract class CommandsMixin {
  @WrapMethod(method = "performCommand")
  private int onPerformCommand(
      ParseResults<CommandSourceStack> parseResults, String command, Operation<Integer> original) {
    CommandSourceStack source = parseResults.getContext().getSource();

    CommandPerformCallback.Companion.getBEFORE().invoker().onCommandPerform(source, command);

    try {
      return original.call(parseResults, command);
    } finally {
      CommandPerformCallback.Companion.getAFTER().invoker().onCommandPerform(source, command);
    }
  }
}
