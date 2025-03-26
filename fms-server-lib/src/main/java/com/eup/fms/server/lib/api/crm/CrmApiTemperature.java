package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.dao.table.redis.OperateEupRedisTableFactory;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.TemperatureBarcode;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmApiTemperature extends CrmApiOperationBase {

    public CrmApiTemperature() {
    }

    public List<String> getTemperatureStandardList(String custImid) {
        try {
            List<String> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , custImid);
            paramObject = createParamJson("TemperatureStandard_Select" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            for (EupJsonObject resultJson : resultList) {
                resultData.add(resultJson.getString("Barcode" , ""));
            }
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Map<String, Map<String, TemperatureBarcode>> getTemperatureBarcodeMap(String custImid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , custImid);
            paramObject = createParamJson("CarItemList_Select_TemperatureBarcode" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            Map<String, Map<String, TemperatureBarcode>> resultMap = new HashMap<>();
            for (EupJsonObject resultJson : resultList) {
                String carUnicode = resultJson.getString("CarUnicode" , "");
                String barcode = resultJson.getString("Barcode" , "");
                String position = resultJson.getString("TemperaturePosition" , "");
                String coefficient = null;
                if (!Strings.isNullOrEmpty(barcode)) {
                    coefficient = OperateEupRedisTableFactory.getInstance().getThermometerCorrection().getCorrectionCoefficient(barcode);
                }
                TemperatureBarcode temperatureBarcode = new TemperatureBarcode(barcode, coefficient);
                resultMap.putIfAbsent(carUnicode, new HashMap<>());
                resultMap.get(carUnicode).put(position, temperatureBarcode);
            }
            return resultMap;
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error(e);
            return new HashMap<>();
        }
    }
}
