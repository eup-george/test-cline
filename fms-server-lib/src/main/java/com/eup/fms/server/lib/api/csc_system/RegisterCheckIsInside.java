package com.eup.fms.server.lib.api.csc_system;

import com.eup.fms.dao.table.logcom.OperateDBLogComFactory;
import com.eup.fms.dao.table.object.db.ms.ctms_center.LocAreaPointPo;
import com.eup.fms.java.lib.component.lognow.base.LogNowInfo;
import com.eup.fms.java.lib.component.lognow.object.ConcreteInfoLocareaObject;
import com.eup.fms.java.lib.shape.AbstractShape;
import com.eup.fms.java.lib.shape.Circle;
import com.eup.fms.java.lib.shape.InOutRange;
import com.eup.fms.java.lib.shape.Polygon;
import com.eup.fms.server.lib.api.csc_system.object.CheckIsInsideEnum;
import com.eup.fms.server.lib.api.csc_system.object.CheckIsInsideObject;
import com.eup.fms.server.lib.golbal_value.GlobalLocAreaValue;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RegisterCheckIsInside {

    private final String team_ID;
    private final String carUnicode;
    private final String type;

    public RegisterCheckIsInside(String _team_ID, String _carUnicode, String _type) {
        this.team_ID = _team_ID;
        this.carUnicode = _carUnicode;
        this.type = _type;
    }

    public CheckIsInsideObject checkIsInside() throws Exception {
        CheckIsInsideObject resultData = new CheckIsInsideObject();
        List<LogNowInfo> logNowData = LogNowInfo.serverDataToLogNowList(OperateDBLogComFactory.getInstance().
            getLogNowData(team_ID, carUnicode, 0, true), false);
        if (logNowData.size() > 0) {
            List<ConcreteInfoLocareaObject> locAreaData = GlobalLocAreaValue.getInstance().getLocAreaData("2001411");
            for (ConcreteInfoLocareaObject locArea : locAreaData) {
                if (!locArea.getFInCharge().equals(type)) {
                    continue;
                }
                AbstractShape shapeBase = getShapeObject(locArea);
                InOutRange inOutRange = shapeBase.isInside(logNowData.get(0).getGisX(), logNowData.get(0).getGisY());
                if (inOutRange == InOutRange.Inside) {
                    resultData.setInside(true);
                    resultData.setFid(locArea.getFId());
                    return resultData;
                }
            }
        }

        resultData.setInside(false);
        resultData.setMessage(CheckIsInsideEnum.NotInLocAreaRange);
        return resultData;
    }

    private AbstractShape getShapeObject(ConcreteInfoLocareaObject locAreaObject) {
        if (locAreaObject.getFShape() == 0) {
            List<Point2D> pointList = new ArrayList<>();
            for (LocAreaPointPo locAreaPoint : locAreaObject.getLocAreaPoints()) {
                Point2D point2D = new Point2D.Double(locAreaPoint.getFGisx() / 1000000.0, locAreaPoint.getFGisy() / 1000000.0);
                pointList.add(point2D);
            }
            return new Polygon(pointList);
        } else {
            Point2D circle = new Point2D.Double(locAreaObject.getFGisx() / 1000000.0, locAreaObject.getFGisy() / 1000000.0);
            return new Circle(circle, locAreaObject.getFLength());
        }
    }
}
