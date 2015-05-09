package main.java.Message;

import org.json.simple.JSONObject;

import java.lang.Override;

public class Message {

    private String id;
    private String messagetext;
    private String username;
    private String idClient;

    public Message(){
    }
    
    public Message(String id, String message, String username, String idClinet) {
        this.id = id;
        this.messagetext = message;
        this.username = username;
        this.idClient = idClient;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getMessageText() {
        return this.messagetext;
    }

    public void setMessageText(String value) {
        this.messagetext = value;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getIDClient() {
        return this.idClient;
    }

    public void setIDClient(String value) {
        this.idClient = value;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("message", this.messagetext);
        jsonObject.put("username", this.username);
        jsonObject.put("idClient", this.idClient);
        return jsonObject.toJSONString();
    }
}
