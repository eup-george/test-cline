package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.BonusDicountDetailObject;
import com.eup.fms.server.lib.api.crm.object.BonusInfoObject;
import com.eup.fms.server.lib.api.crm.object.BonusIntroductionDetailObject;
import com.eup.fms.server.lib.api.crm.object.BonusIntroductionObject;
import com.eup.fms.server.lib.api.crm.object.BonusPointObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.global_infomation.object.UpdataOrInsertResultObject;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonusService extends CrmApiOperationBase {

    /**
     * 取得剩餘紅利 & 累計新增 &累計扣抵
     */
    public BonusPointObject getBonusPoint(String custImid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID", custImid);
            paramObject = createParamJson("Bonus_Select_Total", paramObject);
            CrmApiObject crm_API_Object = callCrmApi(paramObject);
            EupJsonObject resultJSON = crm_API_Object.getResultObject(0);

            BonusPointObject resultData = new BonusPointObject();
            resultData.setRemainBonus(resultJSON.getInt("RemainBonus", 0));
            resultData.setPositiveBonus(resultJSON.getInt("PositiveBonus", 0));
            resultData.setNegativeBonus(resultJSON.getInt("NegativeBonus" , 0));
            resultData.setCarOwnerGasBonus(resultJSON.getInt("CarOwnerGasBonus" , 0));
            resultData.setHaveCarOwnerContract(resultJSON.getBoolean("HaveCarOwnerContract" , false));
            return resultData;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得介紹紀錄 & 成交/未成交紀錄
     */
    public List<BonusIntroductionObject> getIntroductionRecord(String custImid) {
        try {
            List<BonusIntroductionObject> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID", custImid);
            paramObject = createParamJson("Comd_Select_Detail", paramObject);
            CrmApiObject crm_API_Object = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crm_API_Object.getResultObject();

            for (EupJsonObject resultJSON : resultList) {
                BonusIntroductionObject introduction_Object = new BonusIntroductionObject();
                introduction_Object.setType(resultJSON.getBoolean("Comd_Type"));
                introduction_Object.setId(resultJSON.getInt("Comd_ID" , -1));
                introduction_Object.setDate(resultJSON.getString("Comd_Date" , ""));
                introduction_Object.setCompanyName(resultJSON.getString("Comd_CustName" , ""));
                introduction_Object.setName(resultJSON.getString("Comd_Name" , ""));
                introduction_Object.setMemo(resultJSON.getString("Comd_Memo" , ""));
                introduction_Object.setPhone(resultJSON.getString("Comd_Tel" , ""));
                introduction_Object.setPercentage(resultJSON.getInt("Comd_Rate" , 0));

                List<BonusIntroductionDetailObject> introductionDetail_Objects = new ArrayList<>();
                JSONArray details = resultJSON.getJSONArray("Comd_Details");
                for (int i = 0; i < details.length(); i++) {
                    EupJsonObject detail = new EupJsonObject(details.getString(i));
                    BonusIntroductionDetailObject detail_Object = new BonusIntroductionDetailObject();
                    detail_Object.setDate(detail.getString("Comd_LogDate" , ""));
                    detail_Object.setStatus(detail.getInt("Comd_LogStatus" , 0));
                    introductionDetail_Objects.add(detail_Object);
                }
                introduction_Object.setBonusDetail(introductionDetail_Objects);
                resultData.add(introduction_Object);
            }
            return resultData;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 取得客戶紅利扣抵明細
     */
    public List<BonusDicountDetailObject> getDiscountDetail(String cust_IMID, String Type, String StartDate,
                                                            String EndDate) {
        try {
            List<BonusDicountDetailObject> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , cust_IMID);
            paramObject.put("Type" , Type);
            paramObject.put("StartDate" , StartDate);
            paramObject.put("EndDate" , EndDate);
            paramObject = createParamJson("Bonus_Select_Detail" , paramObject);
            CrmApiObject crm_API_Object = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crm_API_Object.getResultObject();

            for (EupJsonObject resultJSON : resultList) {
                BonusDicountDetailObject bonusDicountDetail_Object = new BonusDicountDetailObject();
                bonusDicountDetail_Object.setType(resultJSON.getString("Type" , ""));
                bonusDicountDetail_Object.setSourceType(resultJSON.getInt("BN_SourceType" , 0));
                bonusDicountDetail_Object.setDate(resultJSON.getString("BN_Date" , ""));
                bonusDicountDetail_Object.setCompanyName(resultJSON.getString("Cust_Name" , ""));
                bonusDicountDetail_Object.setQuId(resultJSON.getString("QU_ID" , ""));
                bonusDicountDetail_Object.setDeviceCount(resultJSON.getInt("QU_Count" , 0));
                bonusDicountDetail_Object.setBonus(resultJSON.getDouble("BN_Count" , 0.0));
                bonusDicountDetail_Object.setBonusSum(resultJSON.getDouble("BN_Sum" , 0.0));
                bonusDicountDetail_Object.setMemo(resultJSON.getString("BN_Memo" , ""));
                bonusDicountDetail_Object.setGasContractNumber(resultJSON.getString("GCCContractNumber" , ""));

                List<BonusIntroductionDetailObject> introductionDetail_Objects = new ArrayList<>();
                JSONArray details = resultJSON.getJSONArray("Comd_Details");
                for (int i = 0; i < details.length(); i++) {
                    EupJsonObject detail = new EupJsonObject(details.getString(i));
                    BonusIntroductionDetailObject detail_Object = new BonusIntroductionDetailObject();
                    detail_Object.setDate(detail.getString("Comd_LogDate" , ""));
                    detail_Object.setStatus(detail.getInt("Comd_LogStatus" , 0));
                    introductionDetail_Objects.add(detail_Object);
                }
                bonusDicountDetail_Object.setBonusDetail(introductionDetail_Objects);
                resultData.add(bonusDicountDetail_Object);
            }
            return resultData;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * For Vietnam version
     */
    public List<BonusDicountDetailObject> getDiscountDetail(String customerIMID) {
        try {
            List<BonusDicountDetailObject> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , customerIMID);
            paramObject = createParamJson("Bonus_Select_Detail" , paramObject);
            CrmApiObject crmAPIObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmAPIObject.getResultObject();

            for (EupJsonObject resultJson : resultList) {
                BonusDicountDetailObject bonusDiscountDetailObject = new BonusDicountDetailObject();
                bonusDiscountDetailObject.setType(resultJson.getString("BN_SourceType" , "0"));
                bonusDiscountDetailObject.setDate(resultJson.getString("BN_Date" , ""));
                bonusDiscountDetailObject.setCompanyName(resultJson.getString("Cust_Name" , ""));
                bonusDiscountDetailObject.setQuId(resultJson.getString("QU_ID" , ""));
                bonusDiscountDetailObject.setDeviceCount(resultJson.getInt("QU_Count" , 0));
                bonusDiscountDetailObject.setBonus(resultJson.getInt("BN_Count" , 0));
                bonusDiscountDetailObject.setBonusSum(resultJson.getInt("BN_Sum" , 0));
                bonusDiscountDetailObject.setMemo(resultJson.getString("BN_Memo" , ""));

                List<BonusIntroductionDetailObject> introductionDetailObjects = new ArrayList<>();
                JSONArray details = resultJson.getJSONArray("Comd_Details");
                for (int i = 0; i < details.length(); i++) {
                    EupJsonObject detail = new EupJsonObject(details.getString(i));
                    BonusIntroductionDetailObject detailObject = new BonusIntroductionDetailObject();
                    detailObject.setDate(detail.getString("Comd_LogDate" , ""));
                    detailObject.setStatus(detail.getInt("Comd_LogStatus" , 0));
                    introductionDetailObjects.add(detailObject);
                }
                bonusDiscountDetailObject.setBonusDetail(introductionDetailObjects);
                resultData.add(bonusDiscountDetailObject);
            }
            return resultData;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 取得紅利積點資訊(更新日期、是否第一次推薦)
     */
    public BonusInfoObject getBonusNewsCount(String cust_IMID, String alterDate) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , cust_IMID);
            paramObject.put("BN_AlterDate" , alterDate);
            paramObject = createParamJson("Bonus_Select_Info" , paramObject);

            CrmApiObject crm_API_Object = callCrmApi(paramObject);
            EupJsonObject resultJSON = crm_API_Object.getResultObject(0);
            BonusInfoObject bonusInfo_Object = new BonusInfoObject();
            bonusInfo_Object.setCount(resultJSON.getInt("BN_CNT" , 0));
            bonusInfo_Object.setIntroduced(resultJSON.getBoolean("Recommended" , true));
            return bonusInfo_Object;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 新增我要介紹服務紀錄
     */
    public UpdataOrInsertResultObject<String> InsertComd(String cust_IMID, String custName, String man, String name,
        String tel) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , cust_IMID);
            paramObject.put("Comd_CustName" , custName);
            paramObject.put("Comd_Man" , man);
            paramObject.put("Comd_Name" , name);
            paramObject.put("Comd_Tel" , tel);
            paramObject = createParamJson("Comd_Insert_CustService" , paramObject);

            CrmApiObject crm_API_Object = callCrmApi(paramObject);
            EupJsonObject resultJSON = crm_API_Object.getResultObject(0);
            UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<String>();
            updateResult.setUpdateStatus(resultJSON.getString("Result" , ""));
            return updateResult;
        } catch (Exception e) {
            UpdataOrInsertResultObject<String> updateResult = new UpdataOrInsertResultObject<String>();
            updateResult.setUpdateStatus("Fail");
            return updateResult;
        }
    }
}
