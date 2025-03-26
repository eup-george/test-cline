package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.TrafficExpansionInfoObject;

public class CrmApiTrafficExpansionOperation extends CrmApiOperationBase {

    /**
     * 撈取是否可建立交通車使用單位
     */
    public TrafficExpansionInfoObject customerSelectInfo(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject = createParamJson("TrafficExpansion_Select" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            TrafficExpansionInfoObject trafficExpansionInfoObject = new TrafficExpansionInfoObject();
            trafficExpansionInfoObject.UseQuantity = resultJson.getInt("UseQuantity");
            trafficExpansionInfoObject.MaxCarQuantity = resultJson.getInt("MaxCarQuantity");
            return trafficExpansionInfoObject;
        } catch (Exception e) {
            return null;
        }
    }
}
