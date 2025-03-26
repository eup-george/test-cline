package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.tool.extention.Eup_Objects;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.InvoiceObject;
import com.eup.fms.server.lib.global_infomation.object.UpdataOrInsertResultObject;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CrmApiElectronicInvoice extends CrmApiOperationBase {

    public CrmApiElectronicInvoice() {

    }

    /**
     * 更新客戶線上意願
     */
    public UpdataOrInsertResultObject<String> setElectronicInvoice(String imid, int custElectronicInvoice) {
        UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<>();
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , imid);
            paramObject.put("Cust_ElectronicInvoice" , custElectronicInvoice);
            paramObject = createParamJson("Customer_Update_ToLine" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            updateResult.setUpdateStatus(resultJson.getBoolean("status") ? "SUCCESS" : "Fail");
            updateResult.setUpdateMsg(resultJson.getString("reason"));
            return updateResult;
        } catch (Exception e) {
            updateResult.setUpdateStatus("Fail");
            updateResult.setUpdateMsg("Connection Fail");
            return updateResult;
        }
    }

    /**
     * 取得客戶電子發票清單
     */
    public List<InvoiceObject> getElectronicInvoice(String custImid) throws Exception {
        List<InvoiceObject> resultData = new ArrayList<>();
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("Cust_IMID" , custImid);
        paramObject = createParamJson("Customer_Select_ElectronicInvoice" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<EupJsonObject> resultList = crmApiObject.getResultObject();

        for (EupJsonObject resultJSON : resultList) {
            InvoiceObject invoiceObject = new InvoiceObject();
            invoiceObject.setID(Eup_Objects.forceToInteger(resultJSON.getString("ID"), -1));
            invoiceObject.setCustInvoiceTitle(resultJSON.getString("CustInvoiceTitle"));
            invoiceObject.setCustInvoiceBusinessNumber(resultJSON.getString("CustInvoiceBusinessNumber"));
            invoiceObject.setCustInvoiceAddress(resultJSON.getString("CustInvoiceAddress"));
            invoiceObject.setEmail(resultJSON.getString("Email"));
            invoiceObject.setEmailStatus(Eup_Objects.forceToInteger(resultJSON.getString("EmailStatus"), 0));
            resultData.add(invoiceObject);
        }
        return resultData;
    }

    /**
     * 更新客戶線上意願
     */
    public UpdataOrInsertResultObject<String> updateElectronicInvoice(JSONArray list) {
        UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<>();
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("ElectronicInvoiceList" , list);
            paramObject = createParamJson("Customer_Update_ElectronicInvoice" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            updateResult.setUpdateStatus(resultJson.getBoolean("status") ? "SUCCESS" : "Fail");
            updateResult.setUpdateMsg(resultJson.getString("reason"));
            return updateResult;
        } catch (Exception e) {
            updateResult.setUpdateStatus("Fail");
            updateResult.setUpdateMsg("Connection Fail");
            return updateResult;
        }
    }
}

