package com.teamnoctis.nox;

import com.teamnoctis.nox.interfaces.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.Arrays;

public final class Nox {

    private static final ArrayList<Command> commandsArrayList = new ArrayList<>();

    public static void setCommands(DiscordApi api, Command... commands) {
        commandsArrayList.addAll(Arrays.asList(commands));

        ArrayList<String> names = new ArrayList<>();

        api.getGlobalSlashCommands()
                .thenAccept(globalSlashCommands -> globalSlashCommands.stream().map(SlashCommand::getName)
                        .forEach(names::add));

        commandsArrayList.forEach(command -> {
            if (!names.contains(command.getName())) {
                command.register(api);
            }
        });

        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();

            commandsArrayList.forEach(command -> {
                if (command.getName().equals(interaction.getCommandName())) {
                    command.execute(interaction, interaction.getUser());
                }
            });
        });
    }

    public static ArrayList<Command> getCommands() {
        return commandsArrayList;
    }

}
