package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import java.util.ArrayList;
import java.util.List;

public class CrmApiAllTransferFeeCar extends CrmApiOperationBase {

    public CrmApiAllTransferFeeCar() {
    }

    public List<String> getAllTransferFeeCar() {
        try {
            List<String> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("CarItemList_Select_AllTransferFeeCar" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            for (EupJsonObject resultJson : resultList) {
                resultData.add(resultJson.getString("CarUnicode" , ""));
            }
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
