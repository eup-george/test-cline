package com.eup.fms.server.lib.api.csc_system;

import com.eup.core.component.logger.SysLogTool;
import com.eup.core.component.logger._interface.ILogComponent;
import com.eup.core.component.server_setting.type.service_setting.ServiceSetting;
import com.eup.fms.dao.table.logcom.OperateDBLogComFactory;
import com.eup.fms.dao.table.ms.ctms_center.CustCarRepository;
import com.eup.fms.java.lib.component.lognow.base.LogNowInfo;
import com.eup.fms.server.lib.customer.object.CustomerSettingsBo;
import com.eup.fms.server.lib.golbal_value.cust.GlobalCustData;
import com.eup.fms.server.lib.golbal_value.cust.GlobalCustomerSetting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CscSystemSendLoadData extends CscDataSenderBase {

    public CscSystemSendLoadData(String teamId, String carUnicode) {
        super(teamId, carUnicode);
    }

    public CscSystemSendLoadData(
        String teamId,
        String carUnicode,
        ILogComponent log,
        OperateDBLogComFactory dbLogComFactory,
        CustCarRepository custCarTbOp,
        ServiceSetting serverSetting,
        GlobalCustomerSetting globalCustomerSetting,
        GlobalCustData globalCustData) {
        super(teamId, carUnicode, log, dbLogComFactory, custCarTbOp, serverSetting, globalCustomerSetting, globalCustData);
    }

    @Override
    String getRequestString(String carTel, LogNowInfo logNowInfo) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy,HHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date registerTime;
        if (checkIsUsingCurrentTime(teamId)) {
            registerTime = new Date();
        } else {
            registerTime = logNowInfo.getLogDTime();
        }
        return "$,"
            + carTel.substring(2)
            + ",,"
            + logNowInfo.getLogStatus()
            + ","
            + sdf.format(registerTime)
            + ","
            + formattedToDmm(logNowInfo.getGisY(), true) // N, S
            + ","
            + formattedToDmm(logNowInfo.getGisX(), false) // E, W
            + ","
            + logNowInfo.getLogSpeedKm().intValue()
            + ","
            + logNowInfo.getLogDirect().intValue()
            + ",2,"
            + logNowInfo.getCarNumber()
            + ",CS004,#";
    }

    private boolean checkIsUsingCurrentTime(String teamId) {
        try {
            String custId = globalCustData.getCustIdByTeamId(teamId);
            if (custId.equals("")) {
                return false;
            }
            CustomerSettingsBo customerSettingsObject = globalCustomerSetting.getCustomerSetting(custId);
            List<String> options = customerSettingsObject.getCustOptionListString();
            return options.contains("4ME");
        } catch (Exception e) {
            SysLogTool.getInstance().getLog().error(e);
        }
        return true;
    }

    /**
     * 將經緯度轉為度分格式
     *
     * @param coordinate 經/緯度
     * @param isLatitude 是否為緯度
     * @return 度分格式
     */
    private String formattedToDmm(double coordinate, boolean isLatitude) {
        char direction = isLatitude ? (coordinate >= 0 ? 'N' : 'S') : (coordinate >= 0 ? 'E' : 'W');
        coordinate = Math.abs(coordinate);
        int degrees = (int) coordinate;
        double minutes = (coordinate - degrees) * 60;
        return String.format("%c%d%07.4f" , direction, degrees, minutes);
    }
}
