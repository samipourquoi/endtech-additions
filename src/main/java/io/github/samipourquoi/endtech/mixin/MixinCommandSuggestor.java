package io.github.samipourquoi.endtech.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.samipourquoi.endtech.endbot.EndbotCommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
@Mixin(CommandSuggestor.class)
public abstract class MixinCommandSuggestor {
    @Shadow @Final private TextFieldWidget textField;
    @Shadow private ParseResults<CommandSource> parse;
    @Shadow private boolean completingSuggestions;
    @Shadow private CommandSuggestor.SuggestionWindow window;
    @Shadow @Final private List<OrderedText> messages;
    @Shadow @Final private boolean slashOptional;
    @Shadow @Final private boolean suggestingWhenEmpty;
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow protected abstract void show();
    @Shadow private static int getLastPlayerNameStart(String input) {
        return 0;
    }
    @Shadow @Final private MinecraftClient client;

    /**
     * @reason Include endbot commands suggestions
     * @author samipourquoi
     */
    @Overwrite
    public void refresh() {
        if (this.client.player == null) return;

        String string = this.textField.getText();
        if (this.parse != null && !this.parse.getReader().getString().equals(string)) {
            this.parse = null;
        }

        if (!this.completingSuggestions) {
            this.textField.setSuggestion((String) null);
            this.window = null;
        }

        this.messages.clear();
        StringReader stringReader = new StringReader(string);

        boolean vanillaCommand = stringReader.canRead() && stringReader.peek() == '/';
        boolean endbotCommand = stringReader.canRead() && stringReader.peek() == '!';

        if (vanillaCommand || endbotCommand) {
            stringReader.skip();
        }

        boolean isCommand = this.slashOptional || vanillaCommand || endbotCommand;
        int i = this.textField.getCursor();
        int j;
        if (isCommand) {
            CommandDispatcher<CommandSource> commandDispatcher;

            if (vanillaCommand) {
                commandDispatcher = this.client.player.networkHandler.getCommandDispatcher();
            } else {
                commandDispatcher = EndbotCommandDispatcher.INSTANCE;
            }

            if (this.parse == null) {
                this.parse = commandDispatcher.parse(stringReader, this.client.player.networkHandler.getCommandSource());
            }

            j = this.suggestingWhenEmpty ? stringReader.getCursor() : 1;
            if (i >= j && (this.window == null || !this.completingSuggestions)) {
                this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, i);
                this.pendingSuggestions.thenRun(() -> {
                    if (this.pendingSuggestions.isDone()) {
                        this.show();
                    }
                });
            }
        } else {
            String string2 = string.substring(0, i);
            j = getLastPlayerNameStart(string2);
            Collection<String> collection = this.client.player.networkHandler.getCommandSource().getPlayerNames();
            this.pendingSuggestions = CommandSource.suggestMatching((Iterable)collection, new SuggestionsBuilder(string2, j));
        }

    }
}
