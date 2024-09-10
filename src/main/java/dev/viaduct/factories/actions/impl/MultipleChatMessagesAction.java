package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.utils.Chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChatMessagesAction implements Action {

    private final List<String> messageList;

    public MultipleChatMessagesAction(String... messageList) {
        this.messageList = new ArrayList<>(Arrays.asList(messageList));
    }

    public void addMessage(String message) {
        messageList.add(message);
    }

    @Override
    public void execute(FactoryPlayer factoryPlayer) {
        Chat.tell(factoryPlayer.getPlayer(), messageList);
    }

}
