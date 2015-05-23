package main.java.dao;

import main.java.Message.Message;
import java.util.List;

public interface MessageActionDao {
    List<Message> selectAllMessagesAfterIndex(int index);
    List<Message> selectAllActionsAfterIndex(int index);

    void addMessage(Message message);
    void addAction(Message action);
}
