package auth;


import org.json.JSONObject;
import utils.ConfigReader;

//ui berkaitan dengan locator
//api berkaitan dengan body

//api test bicara soal body data nya -- username dan pass yang didapat dari...properti yang sudah dibuat
public class LoginBody {


    public JSONObject loginValidData() {
        JSONObject body = new JSONObject();
        body.put("usernameOrEmail", ConfigReader.getProperty("usernameOrEmail"));
        body.put("password", ConfigReader.getProperty("password"));
        return body;
    }


    public JSONObject loginInvalidPassData() {
        JSONObject body = new JSONObject();
        body.put("usernameOrEmail", ConfigReader.getProperty("usernameOrEmail"));
        body.put("password", ConfigReader.getProperty("invalidPassword"));
        return body;
    }
}
