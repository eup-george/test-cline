package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrmApiCarItemListOperation extends CrmApiOperationBase {

    public List<String> customerSelectRfid(String custImid) {
        try {
            List<String> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("custIMID" , custImid);
            paramObject = createParamJson("CarItemList_Select_Rfid" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            for (EupJsonObject resultJSON : resultList) {
                resultData.add(resultJSON.getString("CarUnicode"));
            }
            return resultData;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
