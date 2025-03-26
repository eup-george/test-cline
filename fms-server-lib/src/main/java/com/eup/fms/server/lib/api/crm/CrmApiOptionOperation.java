package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.OptionAllListObject;
import java.util.ArrayList;
import java.util.List;

public class CrmApiOptionOperation extends CrmApiOperationBase {

    public List<OptionAllListObject> selectOptions() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("Options_Select" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<OptionAllListObject> resultData = new ArrayList<>();
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
                OptionAllListObject optionAllList = new OptionAllListObject();
                optionAllList.No = quote.getString("No");
                optionAllList.Name = quote.getString("Name");
                optionAllList.Act = quote.getBoolean("Act");
                optionAllList.Kind = quote.getString("Kind");
                optionAllList.customerShow = quote.getBoolean("customerShow");
                optionAllList.common = quote.getBoolean("common");
                resultData.add(optionAllList);
            }
            return resultData;
        } catch (Exception e) {
            return null;
        }
    }
}
