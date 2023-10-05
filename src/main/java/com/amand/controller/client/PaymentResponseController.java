package com.amand.controller.client;

import com.amand.Utils.PaymentUtils;
import com.amand.Utils.YearUtils;
import com.amand.entity.OrderEntity;
import com.amand.entity.PayEntity;
import com.amand.form.OrderForm;
import com.amand.repository.PayRepository;
import com.amand.service.IBagService;
import com.amand.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PaymentResponseController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private PayRepository payRepository;

    @GetMapping("/payment-response")
    public ModelAndView paymentResponse(@RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
                                        @RequestParam("vnp_Amount") String vnp_Amount,
                                        @RequestParam("vnp_BankCode") String vnp_BankCode,
                                        @RequestParam(value = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
                                        @RequestParam("vnp_CardType") String vnp_CardType,
                                        @RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
                                        @RequestParam("vnp_PayDate") String vnp_PayDate,
                                        @RequestParam("vnp_TmnCode") String vnp_TmnCode,
                                        @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
                                        @RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus,
                                        @RequestParam("vnp_TxnRef") String vnp_TxnRef,
                                        @RequestParam("vnp_SecureHash") String vnp_SecureHash,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, ParseException {
        int status = PaymentUtils.isStatusPaymentReturn(vnp_ResponseCode, vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType,
                                                        vnp_OrderInfo, vnp_PayDate, vnp_TmnCode, vnp_TransactionNo,
                                                        vnp_TransactionStatus, vnp_TxnRef, vnp_SecureHash);
        ModelAndView mav = new ModelAndView();
        if (status == 1) {
            OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
            OrderEntity orderEntity = orderService.save(orderForm);
            PayEntity payEntity = new PayEntity();
            payEntity.setVnp_Amount(Double.valueOf(vnp_Amount) / 100);
            payEntity.setVnp_OrderInfo(vnp_OrderInfo);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = formatter.parse(vnp_PayDate);
            formatter.applyPattern("yyyy-MM-dd HH:mm:ss");
            String dateStr = formatter.format(date);
            payEntity.setTime(dateStr);
            payEntity.setVnp_TxnRef(vnp_TxnRef);
            payEntity.setOrder(orderEntity);
            payRepository.save(payEntity);
            session.removeAttribute("orderForm");
            mav.setViewName("redirect:/giao-dich-thanh-cong");
            //Mã giao dịch
            redirectAttributes.addFlashAttribute("vnp_TxnRef", vnp_TxnRef);
            //Số tiền thanh toán
            redirectAttributes.addFlashAttribute("vnp_Amount", vnp_Amount);
            // Đơn vị tiền tệ
            redirectAttributes.addFlashAttribute("vnp_CurrCode", "VND");
            //Ngân hàng
            redirectAttributes.addFlashAttribute("vnp_BankCode", vnp_BankCode);
            //Thông tin giao dịch
            redirectAttributes.addFlashAttribute("vnp_OrderInfo", URLDecoder.decode(vnp_OrderInfo, StandardCharsets.US_ASCII.toString()));
            //Thời gian
            redirectAttributes.addFlashAttribute("Time", dateStr);
            return mav;
        }
        if (status == -1) {
            redirectAttributes.addFlashAttribute("messageError", "Thông tin thanh toán không tồn tại");
            mav.setViewName("redirect:/500");
            return mav;
        } else {
            return new ModelAndView("client/views/PaymentFailed");
        }
    }

}
