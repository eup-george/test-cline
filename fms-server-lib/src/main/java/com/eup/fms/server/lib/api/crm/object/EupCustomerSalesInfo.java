package com.eup.fms.server.lib.api.crm.object;

public class EupCustomerSalesInfo {

    private String custName;
    private String salesMan;
    private String salesManAssistant;
    private String custRegion;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    public String getSalesManAssistant() {
        return salesManAssistant;
    }

    public void setSalesManAssistant(String salesManAssistant) {
        this.salesManAssistant = salesManAssistant;
    }

    public String getCustRegion() {
        return custRegion;
    }

    public void setCustRegion(String custRegion) {
        this.custRegion = custRegion;
    }
}
