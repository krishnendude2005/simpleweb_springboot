package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.RazorpayOrderRequest;
import com.krishnendu.SimpleWebApp.model.RazorpayResponse;
import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Value("${razorpay.api.secret}")
    private String razorpaySecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayKeyId;


    public RazorpayResponse checkoutProduct(RazorpayOrderRequest razorpayOrderRequest) {


        try {
            // creating the razorpay client
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecretKey);

            int amountInPaise = Integer.parseInt(razorpayOrderRequest.getAmount());

            // creating order JSON
            JSONObject orderJson = new JSONObject();
            orderJson.put("amount", amountInPaise);
            orderJson.put("currency", razorpayOrderRequest.getCurrency());


            // create the order
            Order order = razorpay.orders.create(orderJson);

            JSONObject paymentLinkJson = new JSONObject();
            paymentLinkJson.put("amount", amountInPaise);  // must be number
            paymentLinkJson.put("currency", razorpayOrderRequest.getCurrency());
            paymentLinkJson.put("description", "Test payment via Razorpay Payment Link");

            PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkJson);

            return RazorpayResponse.builder()
                    .status("SUCCESS")
                    .message("successfully created razorpay order")
                    .orderId(order.get("id"))
                    .razorpayKey(razorpayKeyId)
                    .amount(Integer.valueOf(razorpayOrderRequest.getAmount()))
                    .currency(razorpayOrderRequest.getCurrency())
                    .paymentUrl(paymentLink.get("short_url"))
                    .build();


        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
            return RazorpayResponse.builder()
                    .status("FAILURE")
                    .message("failed to create razorpay order")
                    .orderId(null)
                    .razorpayKey(razorpayKeyId)
                    .amount(Integer.valueOf(razorpayOrderRequest.getAmount()))
                    .currency(razorpayOrderRequest.getCurrency())
                    .build();
        }

    }

}
