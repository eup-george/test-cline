package com.eup.fms.server.lib.api.crm.object;


public class InvoicePdfObject {

    private String invoice;
    private String invoiceB64Str;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceB64Str() {
        return invoiceB64Str;
    }

    public void setInvoiceB64Str(String invoiceB64Str) {
        this.invoiceB64Str = invoiceB64Str;
    }
}
