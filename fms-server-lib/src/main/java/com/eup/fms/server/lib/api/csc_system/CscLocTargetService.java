package com.eup.fms.server.lib.api.csc_system;

import com.eup.fms.dao.table.object.db.ms.ctms_center.LocTargetPo;
import com.eup.fms.java.lib.shape.InOutRange;
import com.eup.fms.java.lib.shape.Polygon;
import com.eup.fms.server.lib.golbal_value.GlobalLocTargetValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CscLocTargetService {

    private final GlobalLocTargetValue globalLocTargetValue;

    public CscLocTargetService() {
        this(GlobalLocTargetValue.getInstance());
    }

    public CscLocTargetService(GlobalLocTargetValue globalLocTargetValue) {
        this.globalLocTargetValue = globalLocTargetValue;
    }

    public List<LocTargetPo> getLocTargetInArea(String custId, Polygon polygon) throws Exception {
        List<LocTargetPo> allLocTargets = globalLocTargetValue.getLocTargetData(custId);
        List<LocTargetPo> result = new ArrayList<>();
        for (LocTargetPo locTarget : allLocTargets) {
            if (polygon.isInside(locTarget.getGisX().doubleValue(), locTarget.getGisY().doubleValue()) == InOutRange.Inside) {
                result.add(locTarget);
            }
        }
        return result;
    }
}
