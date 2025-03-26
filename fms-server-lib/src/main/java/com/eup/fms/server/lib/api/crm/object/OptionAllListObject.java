package com.eup.fms.server.lib.api.crm.object;

public class OptionAllListObject {

    public String No;
    public String Name;
    public boolean Act;
    public String Kind;
    public boolean customerShow;
    public boolean common;

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isAct() {
        return Act;
    }

    public void setAct(boolean act) {
        Act = act;
    }

    public String getKind() {
        return Kind;
    }

    public void setKind(String kind) {
        Kind = kind;
    }

    public boolean isCustomerShow() {
        return customerShow;
    }

    public void setCustomerShow(boolean customerShow) {
        this.customerShow = customerShow;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }
}
