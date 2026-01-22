package com.krishnendu.SimpleWebApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RazorpayResponse {

    private String status;     // SUCCESS / FAILED
    private String message;    // custom message
    private String orderId;    // Razorpay order id (example: order_XXXXXX)
    private String razorpayKey;  // Razorpay public key
    private Integer amount;    // in paise
    private String currency;   // INR
    private String paymentUrl; // payment URL (simple)

}
