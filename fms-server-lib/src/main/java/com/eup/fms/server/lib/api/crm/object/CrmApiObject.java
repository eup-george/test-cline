package com.eup.fms.server.lib.api.crm.object;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.base.lib.component.EupJsonObject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

public class CrmApiObject {

    private final Integer status;
    private final JSONArray result;
    private final String error;
    private final List<EupJsonObject> resultObjects = new ArrayList<>();

    public CrmApiObject(String param) throws Exception {
        try {
            EupJsonObject resultObject = new EupJsonObject(param);
            status = resultObject.getInt("status");
            result = resultObject.getJSONArray("result");
            error = resultObject.getString("error");

            for (int i = 0; i < result.length(); i++) {
                resultObjects.add(new EupJsonObject(result.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            SysLogTool.getInstance().getLog().error("CRM data parse JSON failed, data: " + param + "\n" + sw, e);
            throw new Exception(param);
        }
    }

    public Integer getStatus() {
        return status;
    }

    public JSONArray getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public List<EupJsonObject> getResultObject() {
        return resultObjects;
    }

    public EupJsonObject getResultObject(int index) {
        try {
            if (index < resultObjects.size()) {
                return resultObjects.get(index);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
