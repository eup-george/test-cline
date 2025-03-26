package com.eup.fms.server.lib.api.crm.plugin.object;

import com.eup.fms.base.lib.component.EupJsonObject;

public class CrmPluginApiBo {

    private final Integer status;
    private final String error;
    private final EupJsonObject resultObjects;

    public CrmPluginApiBo(String param) throws Exception {
        EupJsonObject resultObject = new EupJsonObject(param);
        status = resultObject.getInt("STATUS");
        resultObjects = new EupJsonObject(resultObject.get("SYNC_CONFIG") != null ? resultObject.getString("SYNC_CONFIG") : "{}");
        error = resultObject.getString("MESSAGE");
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public EupJsonObject getResultObject() {
        return resultObjects;
    }
}

