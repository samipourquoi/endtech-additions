package io.github.samipourquoi.endtech.endbot;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.ObjectiveArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EveryScoreboardArgumentType extends ObjectiveArgumentType {
    public static final Pattern EVERY_SCOREBOARD = Pattern.compile("^([mcupbdkz]|kb)-(\\w*)$");
    public static final Pattern WRONG_EVERY_SCOREBOARD = Pattern.compile("^([^mcupbdkz]|kb)-(\\w*)$");
    public static final Iterable<String> PREFIXES = Arrays.asList("m-", "u-", "c-", "b-", "p-", "d-", "k-", "kb-", "z-");
    public static final DynamicCommandExceptionType INCORRECT_PREFIX = new DynamicCommandExceptionType(object -> {
        return new TranslatableText("endtech:arguments.everyscoreboard.incorrect_prefix", object);
    });

    public static EveryScoreboardArgumentType everyObjective() {
        return new EveryScoreboardArgumentType();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Matcher prefixed = EVERY_SCOREBOARD.matcher(builder.getRemaining());
        if (prefixed.matches()) {
            String prefix = prefixed.group(1);
            Set<Identifier> ids = getRegistry(prefix).getIds();
            ArrayList<String> names = new ArrayList<>();
            ids.forEach(id -> {
                names.add(prefix + "-" + id.getPath());
            });
            return CommandSource.suggestMatching(names, builder);
        }
        return CommandSource.suggestMatching(PREFIXES, builder);
    }

    @Override
    public Collection<String> getExamples() {
        return null;
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String string = reader.readUnquotedString();
        Matcher every_scoreboard = EVERY_SCOREBOARD.matcher(string);
        Matcher wrong_every_scoreboard = WRONG_EVERY_SCOREBOARD.matcher(string);
        if (every_scoreboard.matches() || string.equals("clear")) {
            return string;
        } else if (wrong_every_scoreboard.matches()) {
            throw INCORRECT_PREFIX.create(wrong_every_scoreboard.group(1));
        }
        return super.parse(reader);
    }

    private static Registry getRegistry(String string) {
        switch (string) {
            case "m":
                return Registry.BLOCK;
            case "u":
            case "b":
            case "c":
            case "p":
            case "d":
                return Registry.ITEM;
            case "k":
            case "kb":
                return Registry.ENTITY_TYPE;
            case "z":
                return Registry.CUSTOM_STAT;
        }
        return null;
    }
}
