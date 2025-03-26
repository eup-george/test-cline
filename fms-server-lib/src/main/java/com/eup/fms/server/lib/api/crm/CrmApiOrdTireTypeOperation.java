package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmApiOrdTireTypeOperation extends CrmApiOperationBase {

    public CrmApiOrdTireTypeOperation() {
    }

    public Map<String, String> getUnicodeToTireType(String custImid) {
        Map<String, String> unicodeToTireType = new HashMap<>();
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , custImid);
            paramObject = createParamJson("CarItemList_Select_TireType" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            for (EupJsonObject resultJSON : resultList) {
                String unicode = resultJSON.getString("Car_Unicode" , "");
                String tireType = resultJSON.getString("Car_TireType" , "");
                if (!Strings.isNullOrEmpty(unicode)) {
                    unicodeToTireType.put(unicode, tireType);
                }
            }
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error(String.format("getUnicodeToTireType error, imid: %s" , custImid), e);
        }
        return unicodeToTireType;
    }
}
