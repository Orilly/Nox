package com.teamnoctis.nox.collectors;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MessageCollector implements MessageCreateListener {

    private final Messageable channel;

    private final CompletableFuture<Message> collectedMessage = new CompletableFuture<>();

    public MessageCollector(User user, Messageable channel) {
        this.channel = channel;
        user.addMessageCreateListener(this);
        user.getApi().getThreadPool().getScheduler().schedule(() -> collectedMessage.completeExceptionally(new RuntimeException("user took too long to answer")), 30, TimeUnit.MINUTES);
    }

    public CompletableFuture<Message> getCollectedMessage() {
        return collectedMessage;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (!event.getChannel().equals(channel)) {
            return;
        }

        if (!collectedMessage.isDone()) {
            collectedMessage.complete(event.getMessage());
            event.getApi().removeListener(this);
        }
    }

}
