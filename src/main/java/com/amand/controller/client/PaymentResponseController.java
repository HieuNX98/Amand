package com.amand.controller.client;

import com.amand.Utils.PaymentUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentResponseController {

    @GetMapping("/payment-response")
    public ModelAndView paymentResponse(@RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
                                        @RequestParam("vnp_Amount") String vnp_Amount,
                                        @RequestParam("vnp_BankCode") String vnp_BankCode,
                                        @RequestParam("vnp_BankTranNo") String vnp_BankTranNo,
                                        @RequestParam("vnp_CardType") String vnp_CardType,
                                        @RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
                                        @RequestParam("vnp_PayDate") String vnp_PayDate,
                                        @RequestParam("vnp_TmnCode") String vnp_TmnCode,
                                        @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
                                        @RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus,
                                        @RequestParam("vnp_TxnRef") String vnp_TxnRef,
                                        @RequestParam("vnp_SecureHash") String vnp_SecureHash) {
        ModelAndView mav = new ModelAndView("/403");
        int status = PaymentUtils.isStatusPaymentReturn(vnp_ResponseCode, vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType,
                                                        vnp_OrderInfo, vnp_PayDate, vnp_TmnCode, vnp_TransactionNo,
                                                        vnp_TransactionStatus, vnp_TxnRef, vnp_SecureHash);

        if (vnp_ResponseCode.equals("00")) {


        } else {

        }
        return mav;
    }
}
