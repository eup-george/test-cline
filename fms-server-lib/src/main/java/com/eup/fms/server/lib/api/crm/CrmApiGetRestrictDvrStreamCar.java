package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrmApiGetRestrictDvrStreamCar extends CrmApiOperationBase {

    /**
     * 呼叫 CRM API 取得串流限制車輛
     */
    public Set<String> getRestrictDvrStreamCar() {
        Set<String> resultData = new HashSet<>();
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("CarInformation_Select_DvrStreamLimit" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            for (EupJsonObject resultJson : resultList) {
                String carUnicode = resultJson.getString("CarUnicode" , "");
                if (!Strings.isNullOrEmpty(carUnicode)) {
                    resultData.add(carUnicode);
                }
            }
            return resultData;
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error(e);
            resultData.clear();
        }
        return resultData;
    }
}
