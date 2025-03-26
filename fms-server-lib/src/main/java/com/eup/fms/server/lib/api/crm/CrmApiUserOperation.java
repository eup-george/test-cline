package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import java.util.List;

public class CrmApiUserOperation extends CrmApiOperationBase {

    public EupJsonObject staffLogin(String account, String password) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("AC" , account);
            paramObject.put("PW" , password);
            paramObject = createParamJson("EUPUser_Select_Login" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EupJsonObject> getAllUser() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("EUPUser_Select_List" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject();
        } catch (Exception e) {
            return null;
        }
    }

}
