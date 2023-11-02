package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.PaymentRequest;
import com.productservice.dto.response.PaystackResponse;
import com.productservice.service.PaystackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@AllArgsConstructor
@Slf4j
public class PaymentController {

    @Autowired
    private PaystackService paystackService;

    @PostMapping("/makePayment")
    public String makePayment(@ModelAttribute PaymentRequest request, Model model) {
        PaystackResponse response = paystackService.makePayment(request);
        if ("success".equals(response.getStatus())) {
            // Redirect the user to the Paystack payment page
            return "redirect:" + response.getData();

        } else {
            model.addAttribute("errorMessage", response.getMessage());
            return "errorPage";
        }
    }

}
