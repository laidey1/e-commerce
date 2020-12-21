package com.tts.TechTalentStore.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.tts.TechTalentStore.model.ChargeRequest;
import com.tts.TechTalentStore.model.ChargeRequest.Currency;
import com.tts.TechTalentStore.service.StripeService;

import ch.qos.logback.core.joran.spi.ActionException;

@Controller
public class ChargeController {
	 @Autowired
	    private StripeService paymentsService;

	    @PostMapping("/charge")
	    public String charge(ChargeRequest chargeRequest, Model model)
	      throws StripeException, ActionException, AccessException {
	        chargeRequest.setDescription("Example charge");
	        chargeRequest.setCurrency(Currency.EUR);
	        Charge charge = paymentsService.charge(chargeRequest);
	        model.addAttribute("id", charge.getId());
	        model.addAttribute("status", charge.getStatus());
	        model.addAttribute("chargeId", charge.getId());
	        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
	        return "result";
	    }

	    @ExceptionHandler(StripeException.class)
	    public String handleError(Model model, StripeException ex) {
	        model.addAttribute("error", ex.getMessage());
	        return "result";
	    }
}
