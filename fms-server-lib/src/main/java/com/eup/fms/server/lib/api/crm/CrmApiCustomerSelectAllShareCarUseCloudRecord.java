package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CrmApiCustomerSelectAllShareCarUseCloudRecord extends CrmApiOperationBase {

    /**
     * 呼叫 CRM API 取得串流限制車輛
     */
    public boolean getAllShareCarUseCloudRecord(String custImid) {
        try {
            if (custImid == null) {
                return false;
            }
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID", custImid);
            paramObject = createParamJson("Customer_Select_AllShareCarUseCloudRecord", paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            return resultJson.getBoolean("isAllShareCarUseCloudRecord");

        } catch (Exception error) {
            SysLogTool.getInstance().getLog().error("getAllShareCarUseCloudRecord " + ExceptionUtils.getStackTrace(error));
        }
        return false;
    }
}
