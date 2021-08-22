package com.teamnoctis.nox.interfaces;

import com.teamnoctis.nox.annotations.Slash;
import com.teamnoctis.nox.exceptions.CommandNotValidException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public interface Command {

    default String getName() {
        if (getClass().isAnnotationPresent(Slash.class)) {
            return getClass().getAnnotation(Slash.class).name();
        } else {
            throw new CommandNotValidException("Commands must be annotated with the Slash annotation.");
        }
    }

    default String getDescription() {
        if (getClass().isAnnotationPresent(Slash.class)) {
            return getClass().getAnnotation(Slash.class).description();
        } else {
            throw new CommandNotValidException("Commands must be annotated with the Slash annotation.");
        }
    }

    default void register(DiscordApi api) {
        new SlashCommandBuilder()
                .setName(getName())
                .setDescription(getDescription())
                .createGlobal(api)
                .join();
    }

    void execute(SlashCommandInteraction command, User user);

}
