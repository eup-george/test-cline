package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;

public class CrmApiEupUserOperation extends CrmApiOperationBase {

    /**
     * 更新網頁新訊息提醒欄位
     */
    public boolean eupUserUpdateWebNewMessage(Integer eUid) throws Exception {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("EUID" , eUid);
            paramObject = createParamJson("EUPUser_Update_WebNewMessage" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJSON = crmApiObject.getResultObject(0);
            return resultJSON.getBoolean("status");
        } catch (Exception e) {
            return false;
        }
    }
}
