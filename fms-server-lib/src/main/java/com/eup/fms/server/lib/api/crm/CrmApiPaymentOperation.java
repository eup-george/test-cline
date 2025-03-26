package com.eup.fms.server.lib.api.crm;

import com.eup.fms.base.lib.component.EupJsonObject;
import com.eup.fms.server.lib.api.crm.object.CrmApiObject;
import com.eup.fms.server.lib.api.crm.object.PaymentDetailObject;
import com.eup.fms.server.lib.api.crm.object.PaymentPasswordObject;
import com.eup.fms.server.lib.api.crm.object.QuoteMemoObject;
import com.eup.fms.server.lib.customer.payment.PaymentItemDetailBo;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CrmApiPaymentOperation extends CrmApiOperationBase {

    public CrmApiPaymentOperation() {

    }

    /**
     * 檢查帳務密碼
     *
     * @param custImid
     * @param custBillingPw
     * @return
     * @throws Exception
     */
    public PaymentPasswordObject checkPassword(String custImid, String custBillingPw) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("Cust_IMID" , custImid);
        paramObject.put("Cust_BillingPW" , custBillingPw);
        paramObject = createParamJson("Customer_Select_CheckBillingPW" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        PaymentPasswordObject resultData = new PaymentPasswordObject();
        resultData.setStatus(crmApiObject.getResultObject(0).getBoolean("status"));
        resultData.setReason(crmApiObject.getResultObject(0).getString("reason"));
        return resultData;
    }

    /**
     * 更新帳務密碼
     *
     * @param custImid
     * @param custBillingPw
     * @return
     * @throws Exception
     */
    public PaymentPasswordObject updatePassword(String custImid, String custBillingPw) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("Cust_IMID" , custImid);
        paramObject.put("Cust_BillingPW" , custBillingPw);
        paramObject = createParamJson("Customer_Update_BillingPW" , paramObject);

        CrmApiObject crm_API_Object = callCrmApi(paramObject);
        PaymentPasswordObject resultData = new PaymentPasswordObject();
        resultData.setStatus(crm_API_Object.getResultObject(0).getBoolean("status"));
        resultData.setReason(crm_API_Object.getResultObject(0).getString("reason"));
        return resultData;
    }

    /**
     * 取得繳費清單
     *
     * @param custId
     * @return
     * @throws Exception
     */
    public List<PaymentDetailObject> getPaymentList(String custId) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("CustID" , custId);
        paramObject = createParamJson("Quote_Select_GetPayment" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<PaymentDetailObject> resultData = new ArrayList<>();
        for (int i = 0; i < crmApiObject.getResult().length(); i++) {
            EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
            PaymentDetailObject paymentDetailObject = new PaymentDetailObject();
            paymentDetailObject.setQuId(Objects.toString(quote.get("QUID"), ""));
            paymentDetailObject.getCustomer().setCustName(Objects.toString(quote.get("CompanyName"), ""));
            paymentDetailObject.getQuote().setPkName(Objects.toString(quote.get("Type"), ""));
            paymentDetailObject.getQuote().setQuInDate(Objects.toString(quote.get("InDate"), ""));
            paymentDetailObject.getQuote().setQuInvoice(Objects.toString(quote.get("Invoice"), ""));
            paymentDetailObject.getQuote().setQuCollectDate(Objects.toString(quote.get("CollectDate"), null));
            paymentDetailObject.getQuote().setQuSum(Integer.parseInt(Objects.toString(quote.get("Sum"), "0")));
            paymentDetailObject.setIsPaid(Boolean.parseBoolean(Objects.toString(quote.get("IsPaid"), "false")));
            paymentDetailObject.setIsChecked(Boolean.parseBoolean(Objects.toString(quote.get("IsChecked"), "false")));
            paymentDetailObject.setElectronicInvoice(Boolean.parseBoolean(Objects.toString(quote.get("IsElectronicInvoice"), "false")));
            resultData.add(paymentDetailObject);
        }
        return resultData;
    }

    /**
     * 取得已繳費清單
     *
     * @param custId
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public List<PaymentDetailObject> getPaidPaymentList(String custId, String startDate, String endDate)
        throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("CustID" , custId);
        paramObject.put("Start_time" , startDate);
        paramObject.put("End_time" , endDate);
        paramObject = createParamJson("Quote_Select_GetPaidPayment" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<PaymentDetailObject> resultData = new ArrayList<>();
        for (int i = 0; i < crmApiObject.getResult().length(); i++) {
            EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
            PaymentDetailObject paymentDetailObject = new PaymentDetailObject();
            paymentDetailObject.setQuId(Objects.toString(quote.get("QUID"), ""));
            paymentDetailObject.getCustomer().setCustName(Objects.toString(quote.get("CompanyName"), ""));
            paymentDetailObject.getQuote().setPkName(Objects.toString(quote.get("Type"), ""));
            paymentDetailObject.getQuote().setQuInDate(Objects.toString(quote.get("InDate"), ""));
            paymentDetailObject.getQuote().setQuInvoice(Objects.toString(quote.get("Invoice"), ""));
            paymentDetailObject.getQuote().setQuCollectDate(Objects.toString(quote.get("CollectDate"), null));
            paymentDetailObject.getQuote().setQuSum(Integer.parseInt(Objects.toString(quote.get("Sum"), "0")));
            paymentDetailObject.setIsPaid(Boolean.parseBoolean(Objects.toString(quote.get("IsPaid"), "false")));
            paymentDetailObject.setIsChecked(Boolean.parseBoolean(Objects.toString(quote.get("IsChecked"), "false")));
            paymentDetailObject.setElectronicInvoice(Boolean.parseBoolean(Objects.toString(quote.get("IsElectronicInvoice"), "false")));
            resultData.add(paymentDetailObject);
        }
        return resultData;
    }

    /**
     * 取得繳費單明細
     *
     * @param quId
     * @return
     * @throws Exception
     */
    public List<PaymentDetailObject> getPaymentDetail(String quId) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("QUID" , quId);
        paramObject = createParamJson("Quote_Select_GetPaymentQuoteItem" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<PaymentDetailObject> resultData = new ArrayList<>();
        for (int i = 0; i < crmApiObject.getResult().length(); i++) {
            EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
            PaymentDetailObject paymentDetailObject = new PaymentDetailObject();
            paymentDetailObject.setQuId(Objects.toString(quote.get("QUID"), ""));
            paymentDetailObject.getCustomer().setCustName(Objects.toString(quote.get("CompanyName"), ""));
            paymentDetailObject.getQuote().setPkName(Objects.toString(quote.get("Type"), ""));
            paymentDetailObject.getQuote().setQuInDate(Objects.toString(quote.get("InDate"), ""));
            paymentDetailObject.getQuote().setQuInvoice(Objects.toString(quote.get("Invoice"), ""));
            paymentDetailObject.getQuote().setQuCollectDate(Objects.toString(quote.get("CollectDate"), null));
            paymentDetailObject.getQuote().setQuSum(Integer.parseInt(Objects.toString(quote.get("Sum"), "0")));
            paymentDetailObject.getQuote().setQuCarNumber(Objects.toString(quote.get("CarNumber"), "0"));
            paymentDetailObject.getQuote().setCmName(Objects.toString(quote.get("ContactPerson"), ""));
            paymentDetailObject.getQuote().setEupName(Objects.toString(quote.get("EupName"), ""));
            paymentDetailObject.setFirstBarcode(Objects.toString(quote.get("FirstBarcode"), ""));
            paymentDetailObject.setSecondBarcode(Objects.toString(quote.get("SecondBarcode"), ""));
            paymentDetailObject.setThirdBarcode(Objects.toString(quote.get("ThirdBarcode"), ""));
            // 發票相關資料
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceTitle(Objects.toString(quote.get("InvoiceTitle"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceBusinessNumber(Objects.toString(quote.get("BusinessNumber"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceZIPCode(Objects.toString(quote.get("ZIPCode"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceAddress(Objects.toString(quote.get("InvoiceAddress"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceReceiverCompany(Objects.toString(quote.get("InvoiceReceiverCompany"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceReceiverMan(Objects.toString(quote.get("InvoiceReceiverMan"), ""));
            paymentDetailObject.setIsPaid(Objects.toString(quote.get("QU_EupMemo"), "").indexOf("繳費方式") != -1);
            paymentDetailObject.setIsChecked(Objects.toString(quote.get("QU_EupMemo"), "").indexOf("確認日期") != -1);

            List<PaymentItemDetailBo> items = new ArrayList<PaymentItemDetailBo>();
            JSONArray itemArray = quote.getJSONArray("Items");
            for (int j = 0; j < itemArray.length(); j++) {
                EupJsonObject quoteItem = new EupJsonObject(itemArray.getString(j));
                PaymentItemDetailBo paymentItemDetail = new PaymentItemDetailBo();
                paymentItemDetail.getQuoteItem().setQiId(Integer.parseInt(quoteItem.get("ItemID").toString()));
                paymentItemDetail.getQuoteItem().setPiName(quoteItem.get("ItemName").toString());
                paymentItemDetail.getQuoteItem().setQiCount(Integer.parseInt(quoteItem.get("ItemCount").toString()));
                paymentItemDetail.getQuoteItem().setQiPrice(Double.parseDouble(quoteItem.get("ItemPrice").toString()));
                paymentItemDetail.getQuoteItem().setUnitName(quoteItem.get("ItemUnit").toString());
                paymentItemDetail.getQuoteItem().setQiPayMode(Objects.toString(quoteItem.get("PayMode"), ""));
                paymentItemDetail.getQuoteItem().setQiDateStart(Objects.toString(quoteItem.get("DateStart"), ""));
                paymentItemDetail.getQuoteItem().setQiDateEnd(Objects.toString(quoteItem.get("DateEnd"), ""));
                paymentItemDetail.setTotalPrice(Integer.parseInt(quoteItem.get("TotalPrice").toString()));
                items.add(paymentItemDetail);
            }
            paymentDetailObject.setQuoteItemList(items);

            // 備註欄
            JSONArray quoteMemos = new JSONArray(Objects.toString(quote.get("Memo"), ""));
            for (int j = 0; j < quoteMemos.length(); j++) {
                paymentDetailObject.getQuoteMemos().add(quoteMemos.getString(j));
            }

            resultData.add(paymentDetailObject);
        }
        return resultData;
    }

    /**
     * 取得訂單備註
     *
     * @param qUIdList
     * @return
     * @throws Exception
     */
    public List<QuoteMemoObject> quoteSelectMemoList(List<String> qUIdList) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("QUIDList" , qUIdList);
        paramObject = createParamJson("Quote_Select_MemoList" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<QuoteMemoObject> resultData = new ArrayList<>();
        for (int i = 0; i < crmApiObject.getResult().length(); i++) {
            EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
            QuoteMemoObject quoteMemoObject = new QuoteMemoObject();
            quoteMemoObject.QUID = quote.getString("QUID");
            quoteMemoObject.QUEupMemo = quote.getString("QUEupMemo");
            resultData.add(quoteMemoObject);
        }
        return resultData;
    }

    /**
     * 更新訂單備註
     *
     * @param QUMemoList
     * @return
     * @throws Exception
     */
    public boolean quoteUpdateMemoList(List<QuoteMemoObject> QUMemoList) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        JSONArray itemArray = new JSONArray();
        for (QuoteMemoObject QUMemo : QUMemoList) {
            Map<String, Object> map = new HashMap<>();
            map.put("QUID" , QUMemo.QUID);
            map.put("QUEupMemo" , QUMemo.QUEupMemo);
            itemArray.put(map);
        }
        paramObject.put("QUMemoList" , itemArray);
        paramObject = createParamJson("Quote_Update_MemoList" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        EupJsonObject resultJSON = crmApiObject.getResultObject(0);
        return resultJSON.getBoolean("status");
    }

    /**
     * 取得客戶繳費單
     */
    public List<PaymentDetailObject> getpaymentDetailByInvoice(String invoice, boolean haveRead) throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("Invoice" , invoice);
        paramObject.put("HaveRead" , haveRead);
        paramObject = createParamJson("Quote_Select_GetPayment_ByInvoice" , paramObject);
        CrmApiObject crmApiObject = callCrmApi(paramObject);
        List<PaymentDetailObject> resultData = new ArrayList<>();
        for (int i = 0; i < crmApiObject.getResult().length(); i++) {
            EupJsonObject quote = new EupJsonObject(crmApiObject.getResult().getString(i));
            PaymentDetailObject paymentDetailObject = new PaymentDetailObject();
            paymentDetailObject.setQuId(Objects.toString(quote.get("QUID"), ""));
            paymentDetailObject.getCustomer().setCustId(Objects.toString(quote.get("CustID"), ""));
            paymentDetailObject.getCustomer().setCustName(Objects.toString(quote.get("CompanyName"), ""));
            paymentDetailObject.getQuote().setPkName(Objects.toString(quote.get("Type"), ""));
            paymentDetailObject.getQuote().setQuInDate(Objects.toString(quote.get("InDate"), ""));
            paymentDetailObject.getQuote().setQuInvoice(Objects.toString(quote.get("Invoice"), ""));
            paymentDetailObject.getQuote().setQuCollectDate(Objects.toString(quote.get("CollectDate"), null));
            paymentDetailObject.getQuote().setQuSum(Integer.parseInt(Objects.toString(quote.get("Sum"), "0")));
            paymentDetailObject.getQuote().setQuCarNumber(Objects.toString(quote.get("CarNumber"), "0"));
            paymentDetailObject.getQuote().setCmName(Objects.toString(quote.get("ContactPerson"), ""));
            paymentDetailObject.getQuote().setEupName(Objects.toString(quote.get("EupName"), ""));
            paymentDetailObject.setFirstBarcode(Objects.toString(quote.get("FirstBarcode"), ""));
            paymentDetailObject.setSecondBarcode(Objects.toString(quote.get("SecondBarcode"), ""));
            paymentDetailObject.setThirdBarcode(Objects.toString(quote.get("ThirdBarcode"), ""));
            // 發票相關資料
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceTitle(Objects.toString(quote.get("InvoiceTitle"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceBusinessNumber(Objects.toString(quote.get("BusinessNumber"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceZIPCode(Objects.toString(quote.get("ZIPCode"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceAddress(Objects.toString(quote.get("InvoiceAddress"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceReceiverCompany(Objects.toString(quote.get("InvoiceReceiverCompany"), ""));
            paymentDetailObject.getInvoiceListDetail()
                .setIldCustInvoiceReceiverMan(Objects.toString(quote.get("InvoiceReceiverMan"), ""));
            paymentDetailObject.setIsPaid(Objects.toString(quote.get("QU_EupMemo"), "").contains("繳費方式"));
            paymentDetailObject.setIsChecked(Objects.toString(quote.get("QU_EupMemo"), "").contains("確認日期"));

            List<PaymentItemDetailBo> items = new ArrayList<PaymentItemDetailBo>();
            JSONArray itemArray = quote.getJSONArray("Items");
            for (int j = 0; j < itemArray.length(); j++) {
                EupJsonObject quoteItem = new EupJsonObject(itemArray.getString(j));
                PaymentItemDetailBo paymentItemDetail = new PaymentItemDetailBo();
                paymentItemDetail.getQuoteItem().setQiId(Integer.parseInt(quoteItem.get("ItemID").toString()));
                paymentItemDetail.getQuoteItem().setPiName(quoteItem.get("ItemName").toString());
                paymentItemDetail.getQuoteItem().setQiCount(Integer.parseInt(quoteItem.get("ItemCount").toString()));
                paymentItemDetail.getQuoteItem().setQiPrice(Double.parseDouble(quoteItem.get("ItemPrice").toString()));
                paymentItemDetail.getQuoteItem().setUnitName(quoteItem.get("ItemUnit").toString());
                paymentItemDetail.getQuoteItem().setQiPayMode(Objects.toString(quoteItem.get("PayMode"), ""));
                paymentItemDetail.getQuoteItem().setQiDateStart(Objects.toString(quoteItem.get("DateStart"), ""));
                paymentItemDetail.getQuoteItem().setQiDateEnd(Objects.toString(quoteItem.get("DateEnd"), ""));
                paymentItemDetail.setTotalPrice(Integer.parseInt(quoteItem.get("TotalPrice").toString()));
                items.add(paymentItemDetail);
            }
            paymentDetailObject.setQuoteItemList(items);

            // 備註欄
            JSONArray quoteMemos = new JSONArray(Objects.toString(quote.get("Memo"), ""));
            for (int j = 0; j < quoteMemos.length(); j++) {
                paymentDetailObject.getQuoteMemos().add(quoteMemos.getString(j));
            }

            resultData.add(paymentDetailObject);
        }
        return resultData;
    }

    /**
     * 寫入電子發票客戶資料
     */
    public PaymentPasswordObject insertElectronicInvoiceCustInfo(String custImid, String contactMan, String contactTel, String contactEmail)
        throws Exception {
        EupJsonObject paramObject = new EupJsonObject("{}");
        paramObject.put("EICI_CustID" , custImid);
        paramObject.put("EICI_ContactMan" , contactMan);
        paramObject.put("EICI_ContactTel" , contactTel);
        paramObject.put("EICI_ContactEmail" , contactEmail);
        paramObject = createParamJson("ElectronicInvoiceCustInfo_Insert" , paramObject);

        CrmApiObject crmApiObject = callCrmApi(paramObject);
        PaymentPasswordObject resultData = new PaymentPasswordObject();
        resultData.setStatus(crmApiObject.getResultObject(0).getBoolean("status"));
        resultData.setReason(crmApiObject.getResultObject(0).getString("reason"));
        return resultData;
    }
}
