package com.eup.fms.server.lib.api.da_chan;

import com.eup.fms.base.lib.component.EupJsonObject;

public class DaChanApiObject {

    private String status;
    private String value;
    private String message;

    public DaChanApiObject(String param) throws Exception {
        EupJsonObject resultObject = new EupJsonObject(param);
        status = resultObject.has("STATUS") ? resultObject.getString("STATUS") : "";
        value = resultObject.has("VALUE") ? resultObject.getString("VALUE") : "";
        message = resultObject.has("Message") ? resultObject.getString("Message") : "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
