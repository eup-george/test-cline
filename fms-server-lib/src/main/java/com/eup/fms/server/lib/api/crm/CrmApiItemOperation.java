package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.server_setting.ServerSetting;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.core.component.server_setting.type.international_setting.object.InternationalCountry;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.ItemRegionObject;
import java.util.ArrayList;
import java.util.List;

public class CrmApiItemOperation extends CrmApiOperationBase {

    /**
     * 取得TH區域列表
     */
    public List<ItemRegionObject> itemSelectRegion() throws Exception {
        try {
            if (ServerSetting.getInstance().getInternationalSetting().getCountry() != InternationalCountry.TH) {
                return new ArrayList<>();
            }
            List<ItemRegionObject> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("Item_Select_Region" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();

            for (EupJsonObject resultJSON : resultList) {
                ItemRegionObject itemRegionObject = new ItemRegionObject();
                itemRegionObject.RegionNumber = resultJSON.getString("RegionNumber");
                itemRegionObject.RegionName = resultJSON.getString("RegionName");
                resultData.add(itemRegionObject);
            }
            return resultData;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
