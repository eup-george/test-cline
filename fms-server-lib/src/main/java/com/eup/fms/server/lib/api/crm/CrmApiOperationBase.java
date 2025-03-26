package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;

public class CrmApiOperationBase {

    public CrmApiOperationBase() {
    }

    /**
     * 呼叫CRM API
     */
    protected CrmApiObject callCrmApi(EupJsonObject paramObject) throws Exception {
        CrmApiCommand crmApiCommand = new CrmApiCommand();
        String crmApiResult = crmApiCommand.sendCommandToCrmApi(paramObject.toString());
        return new CrmApiObject(crmApiResult);
    }

    /**
     * 產生Param格式
     */
    protected EupJsonObject createParamJson(String methodName, EupJsonObject paramObject) {
        try {
            EupJsonObject postObject = new EupJsonObject("{}");
            postObject.put("MethodName" , methodName);
            postObject.put("Param" , paramObject);
            return postObject;
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("CRM_API_OperationBase createParamJson err" , e);
            return null;
        }
    }
}
