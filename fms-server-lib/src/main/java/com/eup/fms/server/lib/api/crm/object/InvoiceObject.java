package com.eup.fms.server.lib.api.crm.object;

public class InvoiceObject {

    private int ID;
    private String CustInvoiceTitle;
    private String CustInvoiceBusinessNumber;
    private String CustInvoiceAddress;
    private String Email;
    private int EmailStatus;//0未驗證 1已驗證 -1驗證中

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCustInvoiceTitle() {
        return CustInvoiceTitle;
    }

    public void setCustInvoiceTitle(String custInvoiceTitle) {
        CustInvoiceTitle = custInvoiceTitle;
    }

    public String getCustInvoiceBusinessNumber() {
        return CustInvoiceBusinessNumber;
    }

    public void setCustInvoiceBusinessNumber(String custInvoiceBusinessNumber) {
        CustInvoiceBusinessNumber = custInvoiceBusinessNumber;
    }

    public String getCustInvoiceAddress() {
        return CustInvoiceAddress;
    }

    public void setCustInvoiceAddress(String custInvoiceAddress) {
        CustInvoiceAddress = custInvoiceAddress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getEmailStatus() {
        return EmailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        EmailStatus = emailStatus;
    }
}
