package com.eup.fms.server.lib.api;

import com.eup.fms.dao.table.ms.ctms_center.OperateCtmsCenterTableFactory;
import com.eup.fms.dao.table.object.db.ms.ctms_center.customer.CustomerApiTokenPo;

import java.util.List;

public class ApiTokenOperator {

    public List<CustomerApiTokenPo> getByCustId(int custId) throws Exception {
        return OperateCtmsCenterTableFactory.getInstance().getCustomerAPIToken().getByCustId(custId);
    }

    public int insert(CustomerApiTokenPo apiToken) throws Exception {
        return OperateCtmsCenterTableFactory.getInstance().getCustomerAPIToken().insert(apiToken);
    }

    public int delete(String token) throws Exception {
        return OperateCtmsCenterTableFactory.getInstance().getCustomerAPIToken().delete(token);
    }
}
