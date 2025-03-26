package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.SetTypeObject;
import java.util.ArrayList;
import java.util.List;

public class CrmApiSetType extends CrmApiOperationBase {

    public List<SetTypeObject> setTypeSelect() throws Exception {
        try {
            List<SetTypeObject> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("ProKind_Select_SetType" , paramObject);
            CrmApiObject crm_API_Object = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crm_API_Object.getResultObject();

            for (EupJsonObject resultJSON : resultList) {
                SetTypeObject deviceTypeObject = new SetTypeObject();
                deviceTypeObject.setCarType(Integer.parseInt(resultJSON.getString("carType" , "0")));
                deviceTypeObject.setSetType(resultJSON.getString("setType" , "0"));
                resultData.add(deviceTypeObject);
            }
            return resultData;
        } catch (Exception e) {
            throw e;
        }
    }

}
