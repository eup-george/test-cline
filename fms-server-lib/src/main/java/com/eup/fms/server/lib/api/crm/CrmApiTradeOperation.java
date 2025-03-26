package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.dao.table.object.db.ms.eupim.TradePo;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrmApiTradeOperation extends CrmApiOperationBase {

    /**
     * 取得行業別
     */
    public List<TradePo> tradeSelect() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("Trade_Select" , paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<TradePo> resultData = new ArrayList<>();
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
                TradePo trade = new TradePo();
                trade.setTrId(Integer.parseInt(quote.getString("TrID")));
                trade.setTrNo(quote.getString("TrNO"));
                trade.setTrName(quote.getString("TrName"));
                resultData.add(trade);
            }
            return resultData;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<Integer, List<TradePo>> tradeGroupSelect() {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject = createParamJson("TradeGroup_Select" , paramObject);
            Map<Integer, List<TradePo>> resultData = new HashMap<>();
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<TradePo> trData;
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                trData = new ArrayList<>();
                JSONArray trJsonArr = crmApiObject.getResult().getJSONObject(i).getJSONArray("TrList");
                Integer tgId = crmApiObject.getResult().getJSONObject(i).getInt("TgID");
                for (int j = 0; j < trJsonArr.length(); j++) {
                    EupJsonObject tr = new EupJsonObject(trJsonArr.getJSONObject(j).toString());
                    TradePo trade = new TradePo();
                    trade.setTrId(tr.getInt("Tr_ID"));
                    trade.setTrName(tr.getString("Tr_Name"));
                    trData.add(trade);
                }
                resultData.put(tgId, trData);
            }
            return resultData;
        } catch (Exception e) {
            return null;
        }
    }
}
