package main.java.MessageExchange;

import main.java.dao.MessageActionDao;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.java.Message.Message;

import java.util.List;

public class MessageExchange {

    private static JSONParser jsonParser = new JSONParser();

    public static String getToken(int indexMessages,int indexActions) {
        Integer number1 = indexMessages * 8 + 11;
        Integer number2 = indexActions * 8 + 11;
        return "TN" + number1 + "D" + number2 + "EN";
    }

    public static int getIndexMessages(String token) {
        int pos=token.indexOf('D');
        return (Integer.valueOf(token.substring(2, pos)) - 11) / 8;
    }
    
    public static int getIndexActions(String token) {
        Integer pos=token.indexOf('D');
        return (Integer.valueOf(token.substring(pos+1, token.length()-2)) - 11) / 8;
    }
    
    public static String getResponse(int indexMessages,int indexActions,MessageActionDao MADao) {
        JSONObject jsonObject = new JSONObject();
        List<Message> messages=MADao.selectAllMessagesAfterIndex(indexMessages);
        jsonObject.put("messages", messages);
        List<Message> putdeletelist=MADao.selectAllActionsAfterIndex(indexActions);
        jsonObject.put("putdeletelist", putdeletelist);
        jsonObject.put("token", getToken(indexMessages + messages.size(), indexActions + putdeletelist.size()));
        return jsonObject.toJSONString();
    }

    public static Message getClientMessage(String data) throws ParseException {
        Message message=new Message();
        JSONObject jsonobject=getJSONObject(data);
        message.setId((String) jsonobject.get("id"));
        message.setMessageText((String) jsonobject.get("message"));
        message.setUsername((String) jsonobject.get("username"));
        message.setIDClient((String) jsonobject.get("idClient"));
        message.setIsDeleteAction( jsonobject.get("isDeleteAction").toString());
        return  message;
    }

    public static JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
    }
}
