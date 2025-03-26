package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;

public class CrmApiEupMachineOperation extends CrmApiOperationBase {

    /**
     * 使用車代取得車機MAC
     */
    public String eupMachineSelectMachineBarCode(String carUnicode) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("CarUnicode" , carUnicode);
            paramObject = createParamJson("EupMachine_Select_MachineBarCode" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0).getString("MachineBarCode");
        } catch (Exception e) {
            return "";
        }
    }
}
