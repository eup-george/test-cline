package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.tool.Eup_DateTime;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.dao.table.object.db.ms.eupim.ProductAdPo;
import com.eup.fms.dao.table.object.db.ms.eupim.ProductRecommendPo;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class crmApiProductrecommendService extends CrmApiOperationBase {

    /**
     * 取得指定客戶之產品推薦資料
     */
    public List<ProductRecommendPo> productRecommendSelectData(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID", imid);
            paramObject = createParamJson("ProductRecommend_Select_Product", paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<ProductRecommendPo> resultData = new ArrayList<>();
            for (int i = 0; i < crmApiObject.getResult().length(); i++) {
                EupJsonObject resultJSON = new EupJsonObject(crmApiObject.getResult().getString(i));
                ProductRecommendPo productRecommendPo = new ProductRecommendPo();
                productRecommendPo.setPrId(resultJSON.getInt("PR_ID", 0));
                productRecommendPo.setPrTitle(resultJSON.getString("PR_Title"));
                productRecommendPo.setFkPriIdS(resultJSON.getInt("FK_PRI_ID_S", 0));
                productRecommendPo.setFkPriIdB(resultJSON.getInt("FK_PRI_ID_B", 0));
                productRecommendPo.setFkPriIdT(resultJSON.getInt("FK_PRI_ID_T", 0));
                productRecommendPo.setPrUpdateTime(new Eup_DateTime(resultJSON.getString("PR_UpdateTime")));
                productRecommendPo.setPrLink(resultJSON.getString("PR_Link"));
                productRecommendPo.setFkPriIdApp(resultJSON.getInt("FK_PRI_ID_App", 0));
                productRecommendPo.setFkPkId(resultJSON.getInt("PK_ID", 0));
                productRecommendPo.setFkPkName(resultJSON.getString("PK_Name"));
                resultData.add(productRecommendPo);
            }
            return resultData;
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("error:", e);
            return new ArrayList<>();
        }
    }

    /**
     * 取得產品推薦提醒
     */
    public ProductAdPo productRecommendSelectNotice(String imid) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("IMID", imid);
            paramObject = createParamJson("ProductRecommend_Select_Notice", paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            ProductAdPo productAdPo = null;
            // 只取第一筆
            if (crmApiObject.getResult().length() > 0) {
                EupJsonObject resultJSON = new EupJsonObject(crmApiObject.getResult().getString(0));
                productAdPo = new ProductAdPo();
                productAdPo.setPaId(resultJSON.getInt("PA_ID", 0));
                productAdPo.setFkPrId(resultJSON.getInt("FK_PR_ID", 0));
                productAdPo.setFkPriIdWeb(resultJSON.getInt("FK_PRI_ID_Web", 0));
                productAdPo.setFkPriIdApp(resultJSON.getInt("FK_PRI_ID_App", 0));
                productAdPo.setPaUpdateTime(resultJSON.getString("PA_UpdateTime"));
                productAdPo.setPkId(resultJSON.getInt("PK_ID", 0));
                productAdPo.setPkName(resultJSON.getString("PK_Name"));
                productAdPo.setPaButtonURL(resultJSON.has("PA_ButtonURL") ? resultJSON.getString("PA_ButtonURL") : "");
                productAdPo.setPaButtonText(resultJSON.has("PA_ButtonText") ? resultJSON.getString("PA_ButtonText") : "");
                productAdPo.setPaButtonShow(resultJSON.has("PA_ButtonShow") && resultJSON.getBoolean("PA_ButtonShow"));
                productAdPo.setPaButtonNewWindow(resultJSON.has("PA_ButtonNewWindow") && resultJSON.getBoolean("PA_ButtonNewWindow"));
            }
            return productAdPo;
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("error:", e);
            return null;
        }
    }

    /**
     * 取得產品推薦圖片
     */
    public String productRecommendSelectImage(int pictureId) {
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("PRIID", pictureId);
            paramObject = createParamJson("ProductRecommend_Select_Image", paramObject);

            CrmApiObject crmApiObject = callCrmApi(paramObject);
            EupJsonObject resultJSON = crmApiObject.getResultObject(0);
            return resultJSON.getString("Image");
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("error:", e);
            return "";
        }
    }

}
