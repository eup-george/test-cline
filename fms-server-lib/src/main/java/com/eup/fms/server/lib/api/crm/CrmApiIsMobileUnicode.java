package com.eup.fms.server.lib.api.crm;


import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;

public class CrmApiIsMobileUnicode extends CrmApiOperationBase {

    /**
     * 使用車代查詢是否為S007
     */
    public boolean carListSelectIsMobileUnicode(String carUnicode) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("CarUnicode" , carUnicode);
            paramObject = createParamJson("CarList_Select_IsMobileUnicode" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJSON = crmApiObject.getResultObject(0);
            return resultJSON.getBoolean("status");

        } catch (Exception ignored) {
        }
        return false;
    }
}
