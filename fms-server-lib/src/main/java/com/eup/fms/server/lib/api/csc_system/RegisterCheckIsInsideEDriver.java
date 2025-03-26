package com.eup.fms.server.lib.api.csc_system;


import com.eup.fms.dao.table.logcom.OperateDBLogComFactory;
import com.eup.fms.java.lib.component.distance.DistanceTool;
import com.eup.fms.java.lib.component.lognow.base.LogNowInfo;
import com.eup.fms.java.lib.shape.InOutRange;
import com.eup.fms.server.lib.api.csc_system.object.CheckIsInsideEnum;
import com.eup.fms.server.lib.api.csc_system.object.CheckIsInsideObject;
import com.eup.fms.server.lib.api.csc_system.object.CheckIsInsideParam;

import java.util.List;

public class RegisterCheckIsInsideEDriver {

    private final CheckIsInsideParam checkIsInsideParam;

    public RegisterCheckIsInsideEDriver(CheckIsInsideParam _checkIsInsideParam) {
        this.checkIsInsideParam = _checkIsInsideParam;
    }

    public CheckIsInsideObject checkIsInside() throws Exception {
        CheckIsInsideObject resultData = new CheckIsInsideObject();

        boolean isInLocArea = true;
        boolean isInCarRange = true;
        if (checkIsInsideParam.isCheckLocArea()) {
            InOutRange inOutRange = checkIsInsideParam.getShape().isInside(checkIsInsideParam.getCheckGISX(),
                checkIsInsideParam.getCheckGISY());
            isInLocArea = inOutRange == InOutRange.Inside;
        }
        if (checkIsInsideParam.isCheckCarDistance() && !checkIsInsideParam.getTeamId().equals("") && !checkIsInsideParam.getCarUnicode()
            .equals("")) {
            List<LogNowInfo> logNowData = LogNowInfo.serverDataToLogNowList(OperateDBLogComFactory.getInstance().
                getLogNowData(checkIsInsideParam.getTeamId(), checkIsInsideParam.getCarUnicode(), 0, true), false);
            if (logNowData.size() > 0) {
                double distance = DistanceTool.getMilesMDoublePoint(logNowData.get(0).getGisX(), logNowData.get(0).getGisY(),
                    checkIsInsideParam.getCheckGISX(), checkIsInsideParam.getCheckGISY());
                isInCarRange = distance <= 500;
            }
        }

        resultData.setInside(isInLocArea && isInCarRange);
        if (!isInLocArea && !isInCarRange) {
            resultData.setMessage(CheckIsInsideEnum.BothNotInRange);
        } else if (!isInLocArea) {
            resultData.setMessage(CheckIsInsideEnum.NotInLocAreaRange);
        } else if (!isInCarRange) {
            resultData.setMessage(CheckIsInsideEnum.NotInCarRange);
        }
        return resultData;
    }
}
