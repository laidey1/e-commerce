package com.tts.TechTalentStore.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.tts.TechTalentStore.model.ChargeRequest;

import ch.qos.logback.core.joran.spi.ActionException;

@Service
public class StripeService {
	
	 @Value("${STRIPE_PUBLIC_KEY}")
	    private String secretKey;
	    
	    @PostConstruct
	    public void init() {
	        Stripe.apiKey = secretKey;
	    }
	    public Charge charge(ChargeRequest chargeRequest) 
	      throws ActionException, AccessException, StripeException {
	        Map<String, Object> chargeParams = new HashMap<>();
	        chargeParams.put("amount", chargeRequest.getAmount());
	        chargeParams.put("currency", chargeRequest.getCurrency());
	        chargeParams.put("description", chargeRequest.getDescription());
	        chargeParams.put("source", chargeRequest.getStripeToken());
	        return Charge.create(chargeParams);
	    }
}
