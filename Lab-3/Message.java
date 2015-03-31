
import org.json.simple.JSONObject;

public class Message {

    public String message;
    public int id;
    public String username;

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", this.message);
        jsonObject.put("id", this.id);
        jsonObject.put("username", this.username);
        return jsonObject.toJSONString();
    }
}
