package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.ProductRequestStripe;
import com.krishnendu.SimpleWebApp.model.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    static final String SUCCESS = "SUCCESS";
    static final String FAILURE = "FAILURE";

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;



    // Stripe API
    // - ProductName, amount, quantity, currency
    // return => sessionId and url(checkout url)
    public StripeResponse checkoutProducts(ProductRequestStripe productRequestStripe) {
        Stripe.apiKey = stripeSecretKey;

        // define - name
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequestStripe.getName())
                .build();

        // define - currency + amount
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequestStripe.getCurrency() == null ? "inr" : productRequestStripe.getCurrency())
                .setUnitAmount(productRequestStripe.getAmount())
                .setProductData(productData)
                .build();


        // define - quantity
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequestStripe.getQuantity())
                .setPriceData(priceData)
                .build();


        // create the session and pass the above data to the session
        // First - create the session parameters
        // then - create the session object and pass the parameter
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;

        try {
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }


      if(session == null || session.getId() == null || session.getUrl() == null){
          return StripeResponse.builder()
                  .status(StripeService.FAILURE)
                  .message("Error creating stripe session")
                  .build();
      }

      return StripeResponse.builder()
              .status(StripeService.SUCCESS)
              .message("Successfully created stripe session")
              .sessionId(session.getId())
              .sessionUrl(session.getUrl())
              .build();
    }
}
