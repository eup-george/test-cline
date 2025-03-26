package com.eup.fms.server.lib.api.crm.plugin.object;

import com.eup.fms.base.lib.component.EupJsonObject;
import org.codehaus.jettison.json.JSONArray;

public class CrmPluginApiRestBo {

    private final Integer status;
    private final String error;
    private final JSONArray resultObjects;

    public CrmPluginApiRestBo(String param) throws Exception {
        EupJsonObject resultObject = new EupJsonObject(param);
        status = resultObject.getInt("responseStatus");
        resultObjects = resultObject.get("result") != null ? resultObject.getJSONArray("result") : new JSONArray();
        error = resultObject.has("responseMsg") && !resultObject.isNull("responseMsg") ? resultObject.getString("responseMsg") : "";
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public JSONArray getResultObject() {
        return resultObjects;
    }
}
