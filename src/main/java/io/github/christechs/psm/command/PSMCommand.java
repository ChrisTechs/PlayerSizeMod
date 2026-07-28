package io.github.christechs.psm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.github.christechs.psm.ui.screen.PSMConfigMenu;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class PSMCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(PSMCommand::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext registryAccess) {
        dispatcher.register(literal("psm")
                .executes(PSMCommand::executeCommand));
        dispatcher.register(literal("playersizemod").redirect(dispatcher.register(literal("psm"))));
    }

    private static int executeCommand(CommandContext<FabricClientCommandSource> context) {
        Minecraft.getInstance().execute(() ->
                Minecraft.getInstance().setScreenAndShow(new PSMConfigMenu())
        );
        return 1;
    }
}
