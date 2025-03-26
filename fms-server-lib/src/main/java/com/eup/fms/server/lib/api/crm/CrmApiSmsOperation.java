package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.dao.table.object.db.ms.eupim.CommunicationPlatformPo;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.global_infomation.object.UpdataOrInsertResultObject;

public class CrmApiSmsOperation extends CrmApiOperationBase {

    public CrmApiSmsOperation() {

    }

    /**
     * 新增欲發送簡訊紀錄
     */
    public UpdataOrInsertResultObject<String> insertSmsRecord(CommunicationPlatformPo communicationPlatform) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("IMID" , communicationPlatform.getIMID());
        paramObject.put("Type" , communicationPlatform.getType());
        paramObject.put("MsgContent" , communicationPlatform.getMsgContent());
        paramObject.put("Receiver" , communicationPlatform.getReceiver());
        paramObject.put("DTime" , communicationPlatform.getDTime());
        paramObject.put("Status" , communicationPlatform.getStatus());
        paramObject = createParamJson("EupCommunicationPlatform_Insert" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        EupJsonObject resultJSON = crmApiObject.getResultObject(0);
        UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<String>();
        updateResult.setUpdateStatus(resultJSON.getBoolean("status") ? "SUCCESS" : "Fail");
        updateResult.setUpdateMsg(resultJSON.getString("reason"));
        return updateResult;
    }

    public void insertSosSms(String custId, String custImid, String carUnicode) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("custID" , custId);
        paramObject.put("custIMID" , custImid);
        paramObject.put("carUnicode" , carUnicode);
        paramObject = createParamJson("SMSMessageSend_Insert" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        EupJsonObject resultJson = crmApiObject.getResultObject(0);
        UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<String>();
        updateResult.setUpdateStatus(resultJson.getBoolean("status") ? "SUCCESS" : "Fail");
        updateResult.setUpdateMsg(resultJson.getString("reason"));
    }
}
