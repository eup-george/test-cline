package com.eup.fms.server.lib.api.csc_system;

import com.eup.core.component.tool.Eup_DateTime;
import com.eup.fms.dao.table.ms.ctms_center.OperateCtmsCenterTableFactory;
import com.eup.fms.dao.table.object.db.ms.ctms_center.CscLoadDataPo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CscSystemLoadData {

    private final String cust_ID;
    private final String car_Unicode;
    private String startTime;
    private String endTime;

    public CscSystemLoadData(String _cust_ID, String _car_Unicode, String _startTime, String _endTime) {
        this.cust_ID = _cust_ID;
        this.car_Unicode = _car_Unicode;
        this.startTime = _startTime;
        this.endTime = _endTime;
    }

    public List<CscLoadDataPo> loadData() throws Exception {
        List<CscLoadDataPo> result = new ArrayList<>();
        if (startTime.equals("") || endTime.equals("")) {
            Eup_DateTime currentTime = new Eup_DateTime();
            startTime = currentTime.getDateTimeString("yyyy-MM-dd 00:00:00");
            endTime = currentTime.getDateTimeString("yyyy-MM-dd 23:59:59");
        }

        List<Map<String, Object>> resultLoadData = OperateCtmsCenterTableFactory.getInstance().getCSCLoadData()
            .getCSCLoadData(cust_ID, car_Unicode, startTime, endTime);
        for (Map<String, Object> mapData : resultLoadData) {
            CscLoadDataPo data = new CscLoadDataPo();
            data.setSimNumber(Objects.toString(mapData.get("SIM_Number"), ""));
            data.setLoadData(Objects.toString(mapData.get("LoadData"), ""));
            data.setLogDTime(Objects.toString(mapData.get("LogDTime"), ""));
            data.setType(Integer.parseInt(Objects.toString(mapData.get("Type"), "-1")));
            result.add(data);
        }
        return result;
    }
}
