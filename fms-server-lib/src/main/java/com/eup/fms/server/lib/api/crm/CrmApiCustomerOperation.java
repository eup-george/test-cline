package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.tool.extention.Eup_Objects;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.CustomerInfoObject;
import com.eup.fms.server.lib.api.crm.object.EupCustomerSalesInfo;
import com.eup.fms.server.lib.api.crm.object.EupUserInfoObject;
import com.eup.fms.server.lib.global_infomation.object.UpdataOrInsertResultObject;

import java.util.ArrayList;
import java.util.List;

public class CrmApiCustomerOperation extends CrmApiOperationBase {

    /**
     * 取得客戶停用日期
     */
    public EupJsonObject customerSelectCTMSStopDate(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , imid);
            paramObject = createParamJson("Customer_Select_CTMSStopDate" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            return crmApiObject.getResultObject(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得客戶資訊
     */
    public CustomerInfoObject customerSelectInfo(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject = createParamJson("Customer_Select_Info" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            CustomerInfoObject customerInfoObject = new CustomerInfoObject();
            customerInfoObject.CloseAd = resultJson.getBoolean("CloseAd");
            customerInfoObject.TrID = resultJson.getInt("TrID" , null);
            customerInfoObject.TgID = resultJson.getInt("TgID" , null);
            customerInfoObject.ThowNewCarGpsToTHB = resultJson.getBoolean("ThowNewCarGpsToTHB");
            customerInfoObject.Cust_Name = resultJson.getString("Cust_Name" , "");
            customerInfoObject.Cust_Addr = resultJson.getString("Cust_Addr" , "");
            customerInfoObject.Cust_ElectronicInvoice = resultJson.getInt("Cust_ElectronicInvoice" , 2);//0: not confirm 1: agree 2: reject
            return customerInfoObject;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得行業群所有客戶IMID
     */
    public List<String> getCustomerByTgId(int tgId) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("TgId" , tgId);
            paramObject = createParamJson("Customer_Select_ByTgId" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<String> imidList = new ArrayList<>();
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                EupJsonObject imidMap = new EupJsonObject(crmApiObject.getResult().getString(i));
                String imid = imidMap.getString("custId" , "");
                if (imid != null && !imid.equals("")) {
                    imidList.add(imid);
                }
            }
            return imidList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新是否自動轉拋公總欄位
     */
    public UpdataOrInsertResultObject<String> customerUpdateThowNewCarGpsToTHB(String imid,
                                                                               boolean isThowNewCarGpsToTHB) {
        UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<>();
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject.put("ThowNewCarGpsToTHB" , isThowNewCarGpsToTHB);
            paramObject = createParamJson("Customer_Update_ThowNewCarGpsToTHB" , paramObject);

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
     * 取得客戶的負責業務資料
     */
    public EupUserInfoObject customerSelectSalesManInfo(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject = createParamJson("Customer_Select_SalesManInfo" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            EupUserInfoObject eupUserInfoObject = new EupUserInfoObject();
            eupUserInfoObject.EU_ID = resultJson.getString("ID");
            eupUserInfoObject.EU_FirstName = resultJson.getString("FirstName");
            eupUserInfoObject.EU_Sex = resultJson.getString("Sex");
            eupUserInfoObject.EU_Extension = resultJson.getString("Extension");
            eupUserInfoObject.EU_ManagerID = resultJson.getString("ManagerID");
            eupUserInfoObject.EU_LeaderID = resultJson.getString("LeaderID");
            return eupUserInfoObject;
        } catch (Exception e) {
            return null;
        }
    }

    public EupCustomerSalesInfo getCustomerSelectCustomerInfo(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject = createParamJson("Customer_Select_Customer_Info" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            EupCustomerSalesInfo customerSalesInfo = new EupCustomerSalesInfo();
            customerSalesInfo.setCustName(resultJson.getString("Cust_Name"));
            customerSalesInfo.setCustRegion(resultJson.getString("Cust_Region"));
            customerSalesInfo.setSalesMan(resultJson.getString("Cust_SalesMan"));
            customerSalesInfo.setSalesManAssistant(resultJson.getString("Cust_SaleManAssistant"));
            return customerSalesInfo;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得客戶的負責業助資料
     */
    public EupUserInfoObject customerSelectSaleManAssistantInfo(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject = createParamJson("Customer_Select_SaleManAssistantInfo" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            EupUserInfoObject eupUserInfoObject = new EupUserInfoObject();
            eupUserInfoObject.EU_ID = resultJson.getString("ID");
            eupUserInfoObject.EU_FirstName = resultJson.getString("FirstName");
            eupUserInfoObject.EU_Sex = resultJson.getString("Sex");
            eupUserInfoObject.EU_Extension = resultJson.getString("Extension");
            eupUserInfoObject.EU_ManagerID = resultJson.getString("ManagerID");
            eupUserInfoObject.EU_LeaderID = resultJson.getString("LeaderID");
            return eupUserInfoObject;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得客戶簡訊餘額
     */
    public String customerSelectSmsPoints(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID" , imid);
            paramObject = createParamJson("Customer_Select_SmsPoints" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            return resultJson.getString("SmsPoints");
        } catch (Exception e) {
            return "";
        }
    }

    public int getDriverLimitByImid(String custImid) {
        try {
            EupJsonObject param = new EupJsonObject();
            param.put("imid" , custImid);
            param = createParamJson("Customer_Select_DriverMaxCount" , param);
            CrmApiObject crmApiObject = callCrmApi(param);
            EupJsonObject resultJson = crmApiObject.getResultObject(0);
            return Eup_Objects.forceToInteger(resultJson.get("DriverMaxCount"), 0);
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error(e);
            return 0;
        }
    }
}
