package main.java.Storage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import main.java.Message.Message;

public final class MessageAndActionStorage {
    private static final List<Message> MESSAGES = Collections.synchronizedList(new ArrayList<Message>());
    private static final List<Message> ACTIONS = Collections.synchronizedList(new ArrayList<Message>());

    private MessageAndActionStorage() {
    }

    public static List<Message> getMessages() {
        return MESSAGES;
    }

    public static List<Message> getActions() {
        return ACTIONS;
    }

    public static void addMessage(Message message) {
        MESSAGES.add(message);
    }

    public static void addAction(Message action) {
        ACTIONS.add(action);
    }

    public static void addAllMessages(Message[] messages) {
        MESSAGES.addAll(Arrays.asList(messages));
    }

    public static void addAllActions(Message[] actions) {
        ACTIONS.addAll(Arrays.asList(actions));
    }

    public static void addAllMessages(List<Message> messages) {
        MESSAGES.addAll(messages);
    }

    public static void addAllActions(List<Message> actions) {
        ACTIONS.addAll(actions);
    }

    public static int getMessagesSize() {
        return MESSAGES.size();
    }

    public static int getActionsSize() {
        return ACTIONS.size();
    }

    public static List<Message> getMessagesByIndex(int index){
        return MESSAGES.subList(index, MESSAGES.size());
    }

    public static List<Message> getActionsByIndex(int index){
        return ACTIONS.subList(index,ACTIONS.size());
    }
}
