package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmApiAllCarMachineBarCodeOperation extends CrmApiOperationBase {

    /**
     * 使用custImid取得車機MAC
     */
    public Map<String, String> getAllCarMachineBarCode(String custImid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , custImid);
            paramObject = createParamJson("EupMachine_Select_AllCarMachineBarCode" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            Map<String, String> resultMap = new HashMap<>();
            for (EupJsonObject resultJson : resultList) {
                String carUnicode = resultJson.getString("carUnicode" , "");
                String barCode = resultJson.getString("barCode" , "");
                if (resultMap.containsKey(carUnicode)) {
                    continue;
                } else {
                    resultMap.putIfAbsent(carUnicode, barCode);
                }
            }
            return resultMap;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
