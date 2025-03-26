package com.eup.fms.server.lib.api.crm.plugin;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.server.lib.api.crm.plugin.object.CrmPluginApiBo;
import com.eup.fms.server.lib.api.crm.plugin.object.CrmPluginApiSyncConfigObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CrmPluginApiSyncConfigOperation extends CrmPluginApiSerivce {

    public List<CrmPluginApiSyncConfigObject> getSmartBoxOutputCmd(String unicode) {
        try {
            JSONObject paramJson = new JSONObject();
            paramJson.put("METHOD" , "Load_Sync_Config");
            paramJson.put("CAR_UNICODE" , unicode);
            paramJson.put("CAR_SETTYPE" , "EupDVR-HS");
            paramJson.put("PERIPHERAL_SETTYPE" , new JSONArray().put("SMART_BOX"));

            String paramString = createParamString("Param=" + paramJson);
            CrmPluginApiBo crmResultDataObject = callCrmPluginApi(paramString);
            if (crmResultDataObject.getStatus() != 1) {
                return null;
            }
            List<CrmPluginApiSyncConfigObject> resultList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(crmResultDataObject.getResultObject().getString("PERIPHERALS"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject peripherals = jsonArray.getJSONObject(i);
                CrmPluginApiSyncConfigObject crmPluginAPIPeripheralObject = new CrmPluginApiSyncConfigObject(peripherals);
                resultList.add(crmPluginAPIPeripheralObject);
            }
            return resultList;
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("getSmartBoxOutputCmd" , e);
            return new ArrayList<>();
        }
    }

    public int saveSyncConfig(String unicode, JSONObject peripheralObject) {
        try {
            JSONObject paramJson = new JSONObject();
            paramJson.put("METHOD" , "Save_Sync_Config");
            paramJson.put("CAR_UNICODE" , unicode);
            paramJson.put("CAR_SETTYPE" , "EupDVR-HS");
            paramJson.put("SYNC_CONFIG" , new JSONObject().put("PERIPHERALS" , new JSONArray().put(peripheralObject)));

            String paramString = createParamString("Param=" + paramJson);
            CrmPluginApiBo crmResultDataObject = callCrmPluginApi(paramString);

            return crmResultDataObject.getStatus();
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("saveSyncConfig" , e);
            return 0;
        }
    }
}
