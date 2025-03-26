package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import org.codehaus.jettison.json.JSONObject;

public class CrmApiEupMachineSelectIsOwnerMachine extends CrmApiOperationBase {

    /**
     * 檢查是否為我們的機器
     */
    public boolean eupMachineSelectIsOwnerMachine(String barCode) {
        try {

            boolean status = false;
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("BarCode" , barCode);

            EupJsonObject paramObject = new EupJsonObject(jsonParam.toString());

            paramObject = createParamJson("EupMachine_Select_IsOwnerMachine" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);

            if (crmApiObject.getResult().length() > 0) {
                EupJsonObject result = new EupJsonObject(crmApiObject.getResult().getString(0));

                if (result.length() > 0) {
                    status = result.has("status") && result.getBoolean("status");
                }

            }
            return status;
        } catch (Exception e) {
            return false;
        }
    }
}
