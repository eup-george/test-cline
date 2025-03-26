package com.eup.fms.server.lib.api.crm;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.tool.Eup_DateTime;
import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.work_order.WordOrderDetail;
import java.util.ArrayList;
import java.util.List;

public class CrmApiWorkOrderOperation extends CrmApiOperationBase {

    public List<WordOrderDetail> getWorkOrderByCustImid(String custImid, Eup_DateTime startTime, Eup_DateTime endTime) throws Exception {
        List<WordOrderDetail> resultDataList = new ArrayList<>();
        try {
            EupJsonObject paramObject = new EupJsonObject("{}");
            paramObject.put("Cust_IMID" , custImid);
            paramObject.put("Start_Date" , startTime.getDateTimeString("yyyy-MM-dd HH:mm:ss"));
            paramObject.put("End_Date" , endTime.getDateTimeString("yyyy-MM-dd HH:mm:ss"));

            paramObject = createParamJson("WorkOrder_Select" , paramObject);
            CrmApiObject crmApiObject = callCrmApi(paramObject);
            List<EupJsonObject> resultList = crmApiObject.getResultObject();
            for (EupJsonObject resultJson : resultList) {
                WordOrderDetail wordOrderDetail = new WordOrderDetail();
                wordOrderDetail.setCarNumber(resultJson.getString("Car_Number" , ""));
                wordOrderDetail.setAbnormalProblem(resultJson.getString("Abnormal_Problem" , ""));
                wordOrderDetail.setRepairCategory(resultJson.getString("Repair_Category" , ""));
                wordOrderDetail.setRepairer(resultJson.getString("Repairer" , ""));
                wordOrderDetail.setRepairUnit(resultJson.getString("Repair_Unit" , ""));
                wordOrderDetail.setProcessingProgress(resultJson.getString("Repair_Progress" , ""));
                wordOrderDetail.setRepairDate(resultJson.getString("Repair_Date" , ""));
                wordOrderDetail.setProcessingTechnician(resultJson.getString("Processing_Technician" , ""));
                wordOrderDetail.setCompletionDate(resultJson.getString("Completion_Date" , ""));
                wordOrderDetail.setNote(resultJson.getString("Note" , ""));
                wordOrderDetail.setProductCategory(resultJson.getString("ProductCategory" , ""));
                wordOrderDetail.setCompletionDays(resultJson.getString("CompletionDays" , ""));
                resultDataList.add(wordOrderDetail);
            }
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error("getWorkOrderByCustImid error" , e);
        }
        return resultDataList;
    }

}

