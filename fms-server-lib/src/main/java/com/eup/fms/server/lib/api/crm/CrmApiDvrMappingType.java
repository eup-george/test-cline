package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.tool.Eup_DateTime;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmApiDvrMappingType extends CrmApiOperationBase {

    private static Map<String, String> dvrTypeMapping = new HashMap<>();
    private static Eup_DateTime updateTime = new Eup_DateTime().addHour(-2);

    public Map<String, String> getDVRTypeMapping() throws Exception {
        if (new Eup_DateTime().subtract(updateTime).getTotalMinutes() >= 60) {
            Map<String, String> newMapping = proKindDetailSelectDVRMapping();
            if (newMapping != null) {
                dvrTypeMapping = newMapping;
                updateTime = new Eup_DateTime();
            }
        }
        return dvrTypeMapping;
    }

    /**
     * 取得車機影像類型
     */
    private Map<String, String> proKindDetailSelectDVRMapping() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("ProKindDetail_Select_DVRMapping" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            Map<String, String> dvrTypeMapping = new HashMap<>();
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                EupJsonObject dvrType = new EupJsonObject(crmApiObject.getResult().getString(i));
                String pkId = dvrType.getString("PKID" , "");
                if (pkId != null && !pkId.equals("")) {
                    if (!dvrTypeMapping.containsKey(pkId)) {
                        dvrTypeMapping.put(pkId, dvrType.getString("DVRType"));
                    }
                }
            }
            return dvrTypeMapping;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得車機影像類型
     */
    public List<String> proKindDetailSelectFunction(int functionID) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("functionID" , functionID);
            paramObject = createParamJson("ProKindDetail_Select_Function" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);

            List<String> functionList = new ArrayList<>();
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                EupJsonObject dvrType = new EupJsonObject(crmApiObject.getResult().getString(i));
                String pkId = dvrType.getString("PKID" , "");
                if (pkId != null && !pkId.equals("")) {
                    functionList.add(pkId);
                }
            }
            return functionList;
        } catch (Exception e) {
            return null;
        }
    }
}
