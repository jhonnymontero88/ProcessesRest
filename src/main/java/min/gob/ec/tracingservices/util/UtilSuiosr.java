package min.gob.ec.tracingservices.util;

import net.minidev.json.JSONObject;

public class UtilSuiosr {
    public static JSONObject createResponse(String status, String message) {
        JSONObject jo = new JSONObject();
        jo.appendField("status", status);
        jo.appendField("message", message);
        return jo;
    }
}
