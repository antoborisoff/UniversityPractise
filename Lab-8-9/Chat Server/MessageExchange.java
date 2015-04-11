import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class MessageExchange {

    private JSONParser jsonParser = new JSONParser();

    public String getToken(int indexHistory,int indexPutDeleteList) {
        Integer number1 = indexHistory * 8 + 11;
        Integer number2 = indexPutDeleteList * 8 + 11;
        return "TN" + number1 + "D" + number2 + "EN";
    }

    /*public int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }*/

    public int getIndexHistory(String token) {
        int pos=token.indexOf('D');
        return (Integer.valueOf(token.substring(2, pos)) - 11) / 8;
    }
    
    public int getIndexPutDeleteList(String token) {
        Integer pos=token.indexOf('D');
        return (Integer.valueOf(token.substring(pos+1, token.length()-2)) - 11) / 8;
    }
    
    public String getServerResponse(List<Message> messages,List<Message> putdeletelist) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", messages);
        jsonObject.put("putdeletelist", putdeletelist);
        jsonObject.put("token", getToken(messages.size(),putdeletelist.size()));
        return jsonObject.toJSONString();
    }

    public String getClientSendMessageRequest(String message,String id,String username,String idClient) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        jsonObject.put("id",id);
        jsonObject.put("username", username);
        jsonObject.put("idClient", idClient);
        return jsonObject.toJSONString();
    }

    public Message getClientMessage(InputStream inputStream) throws ParseException {
        Message message=new Message();
        JSONObject jsonobject=getJSONObject(inputStreamToString(inputStream));
        message.setMessage((String)jsonobject.get("message"));
        message.setId((String)jsonobject.get("id"));
        message.setUsername((String)jsonobject.get("username"));
        message.setIDClient((String)jsonobject.get("idClient"));
        return  message;
    }

    public JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
    }

    public String inputStreamToString(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        try {
            while ((length = in.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(baos.toByteArray());
    }
}
