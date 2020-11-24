package test01;

import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


public class ExampleSend {
  public ExampleSend(String phone, String msg) {
    String api_key = "NCSZXC8URXP5CANE";
    String api_secret = "LZATGE1QJHNKHRKB3V9T7JWOPLXGXVOI";
    Message coolsms = new Message(api_key, api_secret);

    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phone);
    params.put("from", "01083727270");
    params.put("type", "SMS");
    params.put("text", msg);
    params.put("app_version", "test app 1.2"); // application name and version

    try {
      JSONObject obj = (JSONObject) coolsms.send(params);
      System.out.println(obj.toString());
    } catch (CoolsmsException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getCode());
    }
  }
}
	
