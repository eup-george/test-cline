package com.eup.fms.server.lib.api.stream.object;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.tool.Eup_JSONObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class StreamApiObject {

    private final String responseMessage;
    private final Integer responseStatus;
    private final List<Eup_JSONObject> result;
    private final List<Eup_JSONObject> failResult;

    public StreamApiObject(String param) throws Exception {
        try {
            Eup_JSONObject resultObject = new Eup_JSONObject(param);
            this.responseMessage = resultObject.getString("responseMsg");
            this.responseStatus = resultObject.getInt("responseStatus");

            this.result = new ArrayList<>();
            if (!resultObject.isNull("result")) {
                JSONArray resultData = resultObject.getJSONArray("result");
                for (int i = 0; i < resultData.length(); i++) {
                    result.add(new Eup_JSONObject(resultData.getJSONObject(i).toString()));
                }
            }

            this.failResult = new ArrayList<>();
            if (!resultObject.isNull("failResult")) {
                JSONArray failResultData = resultObject.getJSONArray("failResult");
                for (int i = 0; i < failResultData.length(); i++) {
                    result.add(new Eup_JSONObject(failResultData.getJSONObject(i).toString()));
                }
            }
        } catch (JSONException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            SysLogTool.getInstance().getLog().error("Stream data parse JSON failed, data: " + param + "\n" + sw, e);
            throw new Exception(param);
        }
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public List<Eup_JSONObject> getResult() {
        return result;
    }

    public List<Eup_JSONObject> getFailResult() {
        return failResult;
    }
}
