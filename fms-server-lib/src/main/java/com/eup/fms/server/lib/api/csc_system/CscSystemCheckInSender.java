package com.eup.fms.server.lib.api.csc_system;

import com.eup.fms.java.lib.component.lognow.base.LogNowInfo;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class CscSystemCheckInSender extends CscDataSenderBase {

    private final String locAreaDesc;
    private final String locTargetDesc;

    public CscSystemCheckInSender(String teamId, String carUnicode, String locAreaDesc, String locTargetDesc) {
        super(teamId, carUnicode);
        this.locAreaDesc = locAreaDesc;
        this.locTargetDesc = locTargetDesc;
    }

    @Override
    String getRequestString(String carTel, LogNowInfo logNowInfo) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy,HHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String[] lonLat = logNowInfo.getLogLonLat().split(","); // 度分秒
        if (lonLat.length > 1) {
            return "$,"
                + carTel.substring(2)
                + ",,"
                + logNowInfo.getLogStatus() + ","
                + sdf.format(logNowInfo.getLogDTime()) + ","
                + lonLat[0] + ","
                + lonLat[1] + ","
                + logNowInfo.getLogSpeedKm().intValue() + ","
                + logNowInfo.getLogDirect().intValue() + ","
                + locAreaDesc + ","
                + locTargetDesc + ","
                + logNowInfo.getCarNumber()
                + ",CS004,#";
        }
        throw new IllegalArgumentException("illegal lon lat string");
    }

    @Override
    Map<String, Object> getRequestFieldMap() {
        Map<String, Object> fieldMap = super.getRequestFieldMap();
        fieldMap.put("loc_area_desc" , locAreaDesc);
        fieldMap.put("loc_target_desc" , locTargetDesc);
        return fieldMap;
    }
}
