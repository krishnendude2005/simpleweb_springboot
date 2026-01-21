package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.ProductRequestStripe;
import com.krishnendu.SimpleWebApp.model.StripeResponse;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;



    // Stripe API
    // - ProductName, amount, quantity, currency
    // return => sessionId and url(checkout url)
    public StripeResponse checkoutProducts(ProductRequestStripe productRequestStripe) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequestStripe.getName());
      return  null;
    }
}
