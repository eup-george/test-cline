package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.dao.table.object.db.ms.eupim.CustServicePo;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;

public class CrmApiCustServiceOperation extends CrmApiOperationBase {

    /**
     * 新增服務紀錄
     */
    public boolean custServiceInsert(CustServicePo custService) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("IMID" , custService.getCustId());
        paramObject.put("CMName" , custService.getCmName());
        paramObject.put("CCKind" , custService.getCcKind());
        paramObject.put("EupName" , custService.getEupName());
        paramObject.put("CSContext" , custService.getCsContext());
        paramObject.put("CSIO" , custService.getCsIo());
        paramObject.put("CSContact" , custService.getCsContact());
        paramObject.put("CSPersonnel" , custService.getCsPersonnel());
        paramObject = createParamJson("CustService_Insert" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        EupJsonObject resultJSON = crmApiObject.getResultObject(0);
        return resultJSON.getBoolean("status");
    }

    /**
     * 取得是否申請帳務密碼
     */
    public boolean custServiceSelectIsApplyAccountPW(String imid) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("IMID" , imid);
        paramObject = createParamJson("CustService_Select_IsApplyAccountPW" , paramObject);

        CrmApiObject crm_API_Object = callCrmApi(paramObject);
        EupJsonObject resultJSON = crm_API_Object.getResultObject(0);
        return resultJSON.getBoolean("status");
    }

}
