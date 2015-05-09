package main.java.MessageExchange;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.java.Message.Message;
import main.java.Storage.MessageAndActionStorage;

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
    
    public static String getResponse(int indexMessages,int indexActions) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", MessageAndActionStorage.getMessagesByIndex(indexMessages));
        jsonObject.put("putdeletelist", MessageAndActionStorage.getActionsByIndex(indexActions));
        jsonObject.put("token", getToken(MessageAndActionStorage.getMessagesSize(),MessageAndActionStorage.getActionsSize()));
        return jsonObject.toJSONString();
    }

    public static String getClientSendMessageRequest(String message,String id,String username,String idClient) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("message", message);
        jsonObject.put("username", username);
        jsonObject.put("idClient", idClient);
        return jsonObject.toJSONString();
    }

    public static Message getClientMessage(String data) throws ParseException {
        Message message=new Message();
        JSONObject jsonobject=getJSONObject(data);
        message.setId((String)jsonobject.get("id"));
        message.setMessageText((String)jsonobject.get("message"));
        message.setUsername((String)jsonobject.get("username"));
        message.setIDClient((String)jsonobject.get("idClient"));
        return  message;
    }

    public static JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
    }
}
