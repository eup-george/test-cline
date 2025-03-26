package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.base.lib.extension_methods.EupObjects;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.InvoiceRequestObject;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmApiInvoiceOperation extends CrmApiOperationBase {

    public List<Map<String, Object>> selectInvoiceInfo(String custImid) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("imId" , custImid);
        paramObject = createParamJson("CustInvoiceData_Select_Info" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<EupJsonObject> rawDataList = crmApiObject.getResultObject();
        for (EupJsonObject rawData : rawDataList) {
            Map<String, Object> invoiceSelectInfo = new HashMap<>();
            invoiceSelectInfo.put("serialId" , EupObjects.toInteger(rawData.get("id")));
            invoiceSelectInfo.put("invoiceTitle" , EupObjects.toString(rawData.get("invoiceTitle")));
            invoiceSelectInfo.put("businessNumber" , EupObjects.toString(rawData.get("businessNumber")));
            invoiceSelectInfo.put("specialInvoiceNumber" , EupObjects.toString(rawData.get("specialInvoiceNumber")));
            resultList.add(invoiceSelectInfo);
        }
        return resultList;
    }


    public List<Map<String, Object>> selectCarInvoiceInfo(List<InvoiceRequestObject> invoiceRequestObjectList) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<>();
        EupJsonObject paramObject = new EupJsonObject("{}");
        JSONArray jsonArray = new JSONArray();
        for (InvoiceRequestObject item : invoiceRequestObjectList) {
            Map<String, Object> map = new HashMap<>();
            map.put("IMID" , item.getCustIMID());
            map.put("invoiceTitleID" , item.getCustInvoiceTitleId());
            jsonArray.put(map);
        }
        paramObject.put("invoiceList" , jsonArray);
        paramObject = createParamJson("CarInvoiceData_Select_Info" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<EupJsonObject> rawDataList = crmApiObject.getResultObject();
        for (EupJsonObject rawData : rawDataList) {
            Map<String, Object> invoiceSelectInfo = new HashMap<>();
            invoiceSelectInfo.put("IMID" , EupObjects.toString(rawData.get("IMID")));
            invoiceSelectInfo.put("invoiceTitleID" , EupObjects.toString(rawData.get("invoiceTitleID")));
            invoiceSelectInfo.put("invoiceTitle" , EupObjects.toString(rawData.get("invoiceTitle")));
            resultList.add(invoiceSelectInfo);
        }
        return resultList;
    }
}
