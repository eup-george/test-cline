package com.eup.fms.server.lib.api.crm;


import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.DvrDeviceTypeObject;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CrmApiGetPicDvrUnicode extends CrmApiOperationBase {

    public CrmApiGetPicDvrUnicode() {

    }

    public List<DvrDeviceTypeObject> picDvrUnicodeSelect(String custImid) throws Exception {
        try {
            List<DvrDeviceTypeObject> resultData = new ArrayList<>();
            EupJsonObject paramObject = new EupJsonObject("{}");

            paramObject.put("Cust_ID" , custImid);
            paramObject = createParamJson("CarItemList_Select_PicDvrUnicode" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();

            for (EupJsonObject resultJSON : resultList) {
                DvrDeviceTypeObject deviceTypeObject = new DvrDeviceTypeObject();
                deviceTypeObject.setUnicode(resultJSON.getString("Unicode" , ""));
                List<Integer> channelLs = new ArrayList<>();
                JSONArray channelJsonArr = resultJSON.getJSONArray("ChannelList");
                for (int i = 0; i < channelJsonArr.length(); i++) {
                    channelLs.add(channelJsonArr.getInt(i));
                }
                deviceTypeObject.setChannelLs(channelLs);
                resultData.add(deviceTypeObject);
            }
            return resultData;
        } catch (Exception e) {
            throw e;
        }
    }
}
