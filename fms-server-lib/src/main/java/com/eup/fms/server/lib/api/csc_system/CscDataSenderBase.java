package com.eup.fms.server.lib.api.csc_system;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.logger._interface.ILogComponent;
import com.eup.core.component.server_setting.ServerSetting;
import com.eup.core.component.server_setting.type.service_setting.ServiceSetting;
import com.eup.core.component.tool.extention.Eup_Objects;
import com.eup.fms.dao.table.logcom.OperateDBLogComFactory;
import com.eup.fms.dao.table.ms.ctms_center.CustCarRepository;
import com.eup.fms.dao.table.ms.ctms_center.OperateCtmsCenterTableFactory;
import com.eup.fms.java.lib.component.lognow.base.LogNowInfo;
import com.eup.fms.java.lib.component.socket.EupTcpClinet;
import com.eup.fms.java.lib.component.socket.ServerParam;
import com.eup.fms.java.lib.component.socket.end_string.EndStringSharp;
import com.eup.fms.server.lib.global_infomation.object.UpdataOrInsertResultObject;
import com.eup.fms.server.lib.golbal_value.cust.GlobalCustData;
import com.eup.fms.server.lib.golbal_value.cust.GlobalCustomerSetting;
import com.google.common.base.Strings;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CscDataSenderBase {

    protected final String teamId;
    protected final String carUnicode;
    private final ILogComponent log;
    private final OperateDBLogComFactory dbLogComFactory;
    private final CustCarRepository custCarTbOp;
    private final ServiceSetting serviceSetting;
    protected final GlobalCustomerSetting globalCustomerSetting;
    protected final GlobalCustData globalCustData;

    protected CscDataSenderBase(String teamId, String carUnicode) {
        this(teamId,
            carUnicode,
            SysLogTool.getInstance().getLog(),
            OperateDBLogComFactory.getInstance(),
            OperateCtmsCenterTableFactory.getInstance().getCustCar(),
            ServerSetting.getInstance().getServiceSetting(),
            GlobalCustomerSetting.getInstance(),
            GlobalCustData.getInstance());
    }

    protected CscDataSenderBase(
        String teamId,
        String carUnicode,
        ILogComponent log,
        OperateDBLogComFactory dbLogComFactory,
        CustCarRepository custCarTbOp,
        ServiceSetting serviceSetting,
        GlobalCustomerSetting globalCustomerSetting,
        GlobalCustData globalCustData) {
        this.teamId = teamId;
        this.carUnicode = carUnicode;
        this.log = log;
        this.dbLogComFactory = dbLogComFactory;
        this.custCarTbOp = custCarTbOp;
        this.serviceSetting = serviceSetting;
        this.globalCustomerSetting = globalCustomerSetting;
        this.globalCustData = globalCustData;
    }

    public UpdataOrInsertResultObject<String> getResult() {
        UpdataOrInsertResultObject<String> resultData = new UpdataOrInsertResultObject<>();
        resultData.setUpdateStatus("FAIL");
        Map<String, Object> fieldMap = getRequestFieldMap();
        log.info(fieldMap, "csc_send_data_request");
        try {
            String carTel = getCarTel(carUnicode, teamId);
            LogNowInfo logNowInfo = getLogNow(teamId, carUnicode);
            String requestString = getRequestString(carTel, logNowInfo);
            if (!Strings.isNullOrEmpty(requestString) && sendDataToCsc(requestString, fieldMap)) {
                resultData.setUpdateStatus("SUCCESS");
            }
        } catch (Exception e) {
            log.error(fieldMap, e);
        }
        return resultData;
    }

    boolean sendDataToCsc(String requestString, Map<String, Object> fieldMap) throws IOException {
        fieldMap.put("req" , requestString);

        ServerParam serverParam = new ServerParam();
        serverParam.setCharsets(Charset.forName("BIG5"));
        serverParam.setServerIP(serviceSetting.getCscResident().getUri().getHost());
        serverParam.setServerPort(serviceSetting.getCscResident().getUri().getPort());
        serverParam.setEndDataProcess(new EndStringSharp());
        serverParam.setTimeout(30000);
        EupTcpClinet socketClient = new EupTcpClinet(serverParam, true);
        boolean result = false;
        if (socketClient.connectToServer()) {
            socketClient.sendDataToServer(requestString);
            socketClient.closeClient();
            result = true;
        }

        fieldMap.put("result" , result);
        log.info(fieldMap, "csc_send_data");
        return result;
    }

    Map<String, Object> getRequestFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("team_id" , teamId);
        fieldMap.put("car_unicode" , carUnicode);
        return fieldMap;
    }

    abstract String getRequestString(String carTel, LogNowInfo logNowInfo) throws Exception;

    private LogNowInfo getLogNow(String teamId, String carUnicode) throws Exception {
        List<Map<String, Object>> logNowRawData = dbLogComFactory.getLogNowData(teamId, carUnicode, 0, true);
        List<LogNowInfo> logNowInfoList = LogNowInfo.serverDataToLogNowList(logNowRawData, false);
        if (logNowInfoList.isEmpty()) {
            throw new IllegalStateException("can't find logNow");
        }
        return logNowInfoList.get(0);
    }

    private String getCarTel(String carUnicode, String teamId) throws Exception {
        return custCarTbOp.getCarInfoByCarUnicode(carUnicode, Integer.parseInt(teamId))
            .stream()
            .map(m -> Eup_Objects.toString(m.get("Car_Tel")))
            .filter(s -> !Strings.isNullOrEmpty(s))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("can't find car tel"));
    }
}
