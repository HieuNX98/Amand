package com.amand.Utils;

import com.amand.constant.SystemConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentUtils {

    public static String createURL(Double amount, String codePayment) throws UnsupportedEncodingException {

        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", SystemConstant.vnp_Version);
        vnp_Params.put("vnp_Command", SystemConstant.vnp_Command);
        vnp_Params.put("vnp_TmnCode", SystemConstant.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount.intValue() * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", codePayment);
        vnp_Params.put("vnp_OrderInfo", SystemConstant.vnp_OrderInfo + codePayment);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", SystemConstant.vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", SystemConstant.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = HmacUtils.hmacSHA512(SystemConstant.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = SystemConstant.vnp_Url + "?" + queryUrl;

        return paymentUrl;

    }

    public static int isStatusPaymentReturn(String vnp_ResponseCode, String vnp_Amount, String vnp_BankCode, String vnp_BankTranNo,
                                            String vnp_CardType, String vnp_OrderInfo, String vnp_PayDate, String vnp_TmnCode,
                                            String vnp_TransactionNo, String vnp_TransactionStatus, String vnp_TxnRef,
                                            String vnp_SecureHash) throws UnsupportedEncodingException {

        Map fields = new HashMap<>();
        fields.put("vnp_ResponseCode", vnp_ResponseCode);
        fields.put("vnp_Amount", vnp_Amount);
        fields.put("vnp_BankCode", vnp_BankCode);
        fields.put("vnp_BankTranNo", vnp_BankTranNo);
        fields.put("vnp_CardType", vnp_CardType);
        fields.put("vnp_OrderInfo", URLEncoder.encode(vnp_OrderInfo, StandardCharsets.US_ASCII.toString()));
        fields.put("vnp_PayDate", vnp_PayDate);
        fields.put("vnp_TmnCode", vnp_TmnCode);
        fields.put("vnp_TransactionNo", vnp_TransactionNo);
        fields.put("vnp_TransactionStatus", vnp_TransactionStatus);
        fields.put("vnp_TxnRef", vnp_TxnRef);
        String signValue = hasAllFields(fields);
        if (!signValue.equals(vnp_SecureHash)) {
            return -1;
        }
        if ("00".equals(fields.get("vnp_TransactionStatus"))) {
            return 1;
        } else {
            return 0;
        }
    }

    private static String hasAllFields(Map<String, String> fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while(itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }
        return HmacUtils.hmacSHA512(SystemConstant.vnp_HashSecret, hashData.toString());
    }



}
