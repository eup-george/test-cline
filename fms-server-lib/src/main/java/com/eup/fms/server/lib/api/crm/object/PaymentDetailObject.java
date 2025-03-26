package com.eup.fms.server.lib.api.crm.object;

import com.eup.fms.dao.table.object.db.ms.eup_web_im.InvoiceListDetailPo;
import com.eup.fms.dao.table.object.db.ms.eup_web_im.QuotePo;
import com.eup.fms.dao.table.object.db.ms.eupim.CustomerPo;
import com.eup.fms.server.lib.customer.payment.PaymentItemDetailBo;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailObject {

    private String firstBarcode = "";
    private String secondBarcode = "";
    private String thirdBarcode = "";
    private String quid = "";
    private List<PaymentItemDetailBo> quoteItem;
    private QuotePo quote = new QuotePo();
    private CustomerPo customer = new CustomerPo();
    private InvoiceListDetailPo invoiceListDetail = new InvoiceListDetailPo();
    private String startDate = "";
    private String endDate = "";
    private Boolean isPaid = false;
    private Boolean isChecked = false;
    private List<String> quoteMemos = new ArrayList<>();
    private Boolean isElectronicInvoice = false;


    /**
     * 設定第一個條碼
     */
    public void setFirstBarcode(String _firstBarcode) {
        firstBarcode = _firstBarcode;
    }

    /**
     * 取得第一個條碼
     */
    public String getFirstBarcode() {
        return firstBarcode;
    }

    /**
     * 設定第二個條碼
     */
    public void setSecondBarcode(String _secondBarcode) {
        secondBarcode = _secondBarcode;
    }

    /**
     * 取得第三個條碼
     */
    public String getSecondBarcode() {
        return secondBarcode;
    }

    /**
     * 設定第三個條碼
     */
    public void setThirdBarcode(String _thirdBarcode) {
        thirdBarcode = _thirdBarcode;
    }

    /**
     * 取得第三個條碼
     */
    public String getThirdBarcode() {
        return thirdBarcode;
    }

    /**
     * 設定QU_ID
     */
    public void setQuId(String _quid) {
        quid = _quid;
    }

    /**
     * 取得QU_ID
     */
    public String getQuId() {
        return quid;
    }

    /**
     * 設定詳細清單
     */
    public void setQuoteItemList(List<PaymentItemDetailBo> _quoteItem) {
        quoteItem = _quoteItem;
    }

    /**
     * 取得詳細清單
     */
    public List<PaymentItemDetailBo> getQuoteItemList() {
        return quoteItem;
    }

    /**
     * 取得Quote DB欄位
     */
    public QuotePo getQuote() {
        return quote;
    }

    /**
     * 設定Quote DB欄位
     */
    public void setQuote(QuotePo _quote) {
        this.quote = _quote;
    }

    /**
     * 取得Customer DB欄位
     */
    public CustomerPo getCustomer() {
        return customer;
    }

    /**
     * 設定Customer DB欄位
     */
    public void setCustomer(CustomerPo _customer) {
        this.customer = _customer;
    }

    /**
     * 取得InvoiceData DB欄位
     */
    public InvoiceListDetailPo getInvoiceListDetail() {
        return invoiceListDetail;
    }

    /**
     * 設定InvoiceData DB欄位
     */
    public void setInvoiceListDetail(InvoiceListDetailPo _invoiceListDetail) {
        this.invoiceListDetail = _invoiceListDetail;
    }

    /**
     * 取得結算區間 - 起
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 設定結算區間 - 起
     */
    public void setStartDate(String _startDate) {
        startDate = _startDate;
    }

    /**
     * 取得結算區間 - 訖
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 設定結算區間 - 訖
     */
    public void setEndDate(String _endDate) {
        endDate = _endDate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public List<String> getQuoteMemos() {
        return quoteMemos;
    }

    public void setQuoteMemos(List<String> quoteMemos) {
        this.quoteMemos = quoteMemos;
    }

    public Boolean getElectronicInvoice() {
        return isElectronicInvoice;
    }

    public void setElectronicInvoice(Boolean electronicInvoice) {
        isElectronicInvoice = electronicInvoice;
    }
}
