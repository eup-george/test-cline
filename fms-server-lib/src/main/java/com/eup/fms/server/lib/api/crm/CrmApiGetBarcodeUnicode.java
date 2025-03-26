package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrmApiGetBarcodeUnicode extends CrmApiOperationBase {

    public CrmApiGetBarcodeUnicode() {

    }

    public Set<String> getTrailerHeadCarUnicodes() throws Exception {
        Set<String> unicodes = new HashSet<>();
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject = createParamJson("CarItemList_Select_AllTrailerCar" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<EupJsonObject> resultList = crmApiObject.getResultObject();
        for (EupJsonObject resultJson : resultList) {
            String carUnicode = resultJson.getString("CarUnicode" , "");
            if (!Strings.isNullOrEmpty(carUnicode)) {
                unicodes.add(carUnicode);
            }
        }
        return unicodes;
    }

    public Set<String> getChildCarUnicodes() throws Exception {
        Set<String> unicodes = new HashSet<>();

        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject = createParamJson("CarItemList_Select_AllPalletCar" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<EupJsonObject> resultList = crmApiObject.getResultObject();

        for (EupJsonObject json : resultList) {
            unicodes.add(json.getString("CarUnicode" , ""));
        }

        return unicodes;
    }
}
