package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.golbal_value.object.CarInfoRfidBo;
import java.util.ArrayList;
import java.util.List;

public class CrmApiCarSettingOperation extends CrmApiOperationBase {

    public List<CarInfoRfidBo> getInfoCarSetRfid418() {
        try {
            List<CarInfoRfidBo> carInfoRfidBoList = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("CarItemList_Select_AllRFID418", paramObject);
            CrmApiObject result = callCrmApi(paramObject);
            List<EupJsonObject> resultList = result.getResultObject();
            for (EupJsonObject resultJSON : resultList) {
                CarInfoRfidBo item = CarInfoRfidBo.builder()
                    .carUnicode(resultJSON.getString("CarUnicode"))
                    .custId(resultJSON.getString("CustID"))
                    .build();
                carInfoRfidBoList.add(item);
            }
            return carInfoRfidBoList;
        } catch (Exception e) {
            return null;
        }
    }
}
