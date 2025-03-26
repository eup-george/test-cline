package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.dao.table.object.db.ms.ctms_center.CustCarPo;
import com.eup.fms.server.lib.api.crm.object.CrmApiCarInfoObject;
import com.eup.fms.server.lib.api.crm.object.SetTypeObject;
import com.eup.fms.server.lib.api.crm.plugin.CrmPluginApiSerivce;
import com.eup.fms.server.lib.api.crm.plugin.object.CrmPluginApiBo;
import com.eup.fms.server.lib.api.crm.plugin.object.CrmPluginApiRestBo;
import com.eup.fms.server.lib.api.sync_setting.SyncSettingApi;
import com.eup.fms.server.lib.global_infomation.object.UpdataOrInsertResultObject;
import com.eup.fms.server.lib.sync.bo.SyncConfigBo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class CrmApiCarInfoService extends CrmPluginApiSerivce {

    private List<SetTypeObject> setTypeList = null;

    public List<CrmApiCarInfoObject> getSyncConfig(String custId, List<CustCarPo> lstCarData) throws Exception {
        List<CrmApiCarInfoObject> lstResult = new ArrayList<>();
        for (CustCarPo data : lstCarData) {
            String carUnicode = data.getCarUnicode();
            String carType = data.getCarType();
            String carSetType = getCarSetType(carType);

            String paramString = "Param={METHOD:\"Load_Sync_Config\",\"CAR_UNICODE\":" + carUnicode + ",\"CAR_SETTYPE\":" + carSetType
                + ",\"PERIPHERAL_SETTYPE\":[],\"EDITOR\":" + custId + "}";
            paramString = createParamString(paramString);
            CrmPluginApiBo crmResultDataObject = callCrmPluginApi(paramString);
            String valueUpdated = "0:0:0";
            int syncStatus = 0;
            if (crmResultDataObject.getStatus() != 1) {
                continue;
            }
            JSONArray obJson = new JSONArray(crmResultDataObject.getResultObject().getString("DEVICE"));
            String settingName = "no_buzz";
            for (int i = 0; i < obJson.length(); i++) {
                EupJsonObject resultObject = new EupJsonObject(obJson.get(i).toString());
                if (!resultObject.get("SETTING_NAME").toString().equals("S168II_Buzzer")
                    && !resultObject.get("SETTING_NAME").toString().equals("GoTrack_Buzzer_Mode")) {
                    continue;
                }
                settingName = resultObject.get("SETTING_NAME").toString();
                syncStatus = Integer.parseInt(resultObject.get("SYNC_STATUS").toString());
                JSONArray settingDetails = new JSONArray(resultObject.get("SETTING_DETAILS").toString());
                EupJsonObject settingDetail = new EupJsonObject(settingDetails.get(0).toString());
                valueUpdated = Objects.toString(settingDetail.get("VALUE_UPDATED"), "0:0:0");
                if (valueUpdated.equals("0:0")) {
                    valueUpdated = "0:0:0";
                }
                if (valueUpdated.equals("1:0")) {
                    valueUpdated = "1:0:0";
                }
                if (valueUpdated.equals("0:1")) {
                    valueUpdated = "0:1:0";
                }
                if (valueUpdated.equals("1:1")) {
                    valueUpdated = "1:1:0";
                }
            }
            CrmApiCarInfoObject result = new CrmApiCarInfoObject();
            result.setCarUnicode(carUnicode);
            result.setValueUpdated(valueUpdated);
            result.setSyncStatus(syncStatus);
            result.setBuzzSettingName(settingName);
            lstResult.add(result);
        }
        return lstResult;
    }

    private String getCarSetType(String carType) throws Exception {
        if (setTypeList == null) {
            CrmApiSetType crmApiSetType = new CrmApiSetType();
            setTypeList = crmApiSetType.setTypeSelect();
        }
        String carSetType = "REBIT";
        if (!carType.isEmpty()) {
            for (SetTypeObject setType : setTypeList) {
                if (setType.getCarType().toString().equals(carType)) {
                    return setType.getSetType();
                }
            }
        }
        return carSetType;
    }

    public UpdataOrInsertResultObject<String> setSyncConfig(String custID, String valueUpdate, String unicode, String carType) throws Exception {
        boolean updateStatus = true;
        UpdataOrInsertResultObject<String> singleMap = new UpdataOrInsertResultObject<String>();
        List<String> resultData = new ArrayList<>();
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("METHOD", "Save_Sync_Config");
        paramObject.put("CAR_UNICODE", unicode);
        paramObject.put("CAR_SETTYPE", getCarSetType(carType));
        paramObject.put("EDITOR", custID);
        EupJsonObject syncConfigObject = new EupJsonObject("{}");
        JSONArray obJson = new JSONArray();
        JSONArray obDetailJson = new JSONArray();
        EupJsonObject peripheraLsObject = new EupJsonObject("{}");
        EupJsonObject peripheraDetailsLsObject = new EupJsonObject("{}");
        peripheraLsObject.put("SETTING_NAME", "S168II_Buzzer");
        peripheraLsObject.put("CMD_SELECTOR", "set");
        peripheraLsObject.put("CRUD_ACTION", "insert_or_update");
        peripheraLsObject.put("IS_CMD_ACTIVATE", true);
        peripheraLsObject.put("IS_CMD_IMMEDIATE", true);
        peripheraDetailsLsObject.put("DETAIL_DISPLAY_NAME", "S168II_Buzzer");
        peripheraDetailsLsObject.put("DETAIL_SETTING_NAME", "S168II_Buzzer");
        peripheraDetailsLsObject.put("VALUE_UPDATED", valueUpdate);
        obDetailJson.put(peripheraDetailsLsObject);
        peripheraLsObject.put("SETTING_DETAILS", obDetailJson);
        obJson.put(peripheraLsObject);
        syncConfigObject.put("DEVICE", obJson);
        paramObject.put("SYNC_CONFIG", syncConfigObject);

        String paramString = "";
        paramString = "Param=" + paramObject.toString();
        paramString = createParamString(paramString);
        CrmPluginApiBo crmResultDataObject = callCrmPluginApi(paramString);
        if (crmResultDataObject.getStatus() != 1) {
            resultData.add(unicode);
            updateStatus = false;
        }
        if (updateStatus) {
            singleMap.setUpdateStatus("SUCCESS");
            singleMap.setResultList(null);
        } else {
            singleMap.setUpdateStatus("FAIL");
            singleMap.setResultList(resultData);
        }
        return singleMap;
    }

    public UpdataOrInsertResultObject<String> setSyncConfigGoDevice(String custID, String valueUpdate, String unicode, String carType)
        throws Exception {
        boolean updateStatus = true;
        UpdataOrInsertResultObject<String> singleMap = new UpdataOrInsertResultObject<String>();
        List<String> resultData = new ArrayList<>();
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("METHOD", "Save_Sync_Config");
        paramObject.put("CAR_UNICODE", unicode);
        paramObject.put("CAR_SETTYPE", getCarSetType(carType));
        paramObject.put("EDITOR", custID);
        EupJsonObject syncConfigObject = new EupJsonObject("{}");
        JSONArray obJson = new JSONArray();
        JSONArray obDetailJson = new JSONArray();
        EupJsonObject peripheraLsObject = new EupJsonObject("{}");
        EupJsonObject peripheraDetailsLsObject = new EupJsonObject("{}");
        peripheraLsObject.put("SETTING_NAME", "GoTrack_Buzzer_Mode");
        peripheraLsObject.put("CMD_SELECTOR", "set");
        peripheraLsObject.put("CRUD_ACTION", "insert_or_update");
        peripheraLsObject.put("IS_CMD_ACTIVATE", true);
        peripheraLsObject.put("IS_CMD_IMMEDIATE", true);
        peripheraDetailsLsObject.put("DETAIL_DISPLAY_NAME", "GoTrack_Buzzer_Mode");
        peripheraDetailsLsObject.put("DETAIL_SETTING_NAME", "GoTrack_Buzzer_Mode");
        peripheraDetailsLsObject.put("VALUE_UPDATED", valueUpdate);
        obDetailJson.put(peripheraDetailsLsObject);
        peripheraLsObject.put("SETTING_DETAILS", obDetailJson);
        obJson.put(peripheraLsObject);
        syncConfigObject.put("DEVICE", obJson);
        paramObject.put("SYNC_CONFIG", syncConfigObject);

        String paramString = "";
        paramString = "Param=" + paramObject.toString();
        paramString = createParamString(paramString);
        CrmPluginApiBo crmResultDataObject = callCrmPluginApi(paramString);
        if (crmResultDataObject.getStatus() != 1) {
            resultData.add(unicode);
            updateStatus = false;
        }
        if (updateStatus) {
            singleMap.setUpdateStatus("SUCCESS");
            singleMap.setResultList(null);
        } else {
            singleMap.setUpdateStatus("FAIL");
            singleMap.setResultList(resultData);
        }
        return singleMap;
    }

}
