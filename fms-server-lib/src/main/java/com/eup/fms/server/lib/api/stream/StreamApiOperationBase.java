package com.eup.fms.server.lib.api.stream;

import com.eup.core.component.tool.Eup_JSONObject;
import com.eup.fms.server.lib.api.stream.object.StreamApiObject;

public class StreamApiOperationBase {

    public StreamApiOperationBase() {

    }

    protected StreamApiObject callStreamApi(String path, Eup_JSONObject paramObject) throws Exception {
        StreamApiCommand streamApiCommand = new StreamApiCommand();
        String crmApiResult = streamApiCommand.sendCommandToStreamApi(path, paramObject != null ? paramObject.toString() : null);
        return new StreamApiObject(crmApiResult);
    }

}
