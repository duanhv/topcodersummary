/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.controller.signup;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import zho.com.fw.bean.CustomerBean;
import zho.com.fw.controller.signin.SignInUtils;
import zho.com.fw.exception.UsernameAlreadyInUseException;
import zho.com.fw.info.Customer;
import zho.com.fw.message.Message;
import zho.com.fw.message.MessageType;
import zho.com.fw.service.AccountService;

@Controller
public class SignupController {

	private final AccountService accountService;

	@Inject
	public SignupController(AccountService accountService) {
		this.accountService = accountService;
	}

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public SignupForm signupForm(WebRequest request) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {
			request.setAttribute("message", new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Spring Social Showcase account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
			return SignupForm.fromProviderUser(connection.fetchUserProfile());
		} else {
			return new SignupForm();
		}
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
		if (formBinding.hasErrors()) {
			return null;
		}
		Customer customer = createCustomer(form, formBinding);
		if (customer != null) {
			SignInUtils.signin(customer.getUsername());
			ProviderSignInUtils.handlePostSignUp(customer.getUsername(), request);
			return "redirect:/";
		}
		return null;
	}

	// internal helpers
	
	private Customer createCustomer(SignupForm form, BindingResult formBinding) {
		try {
			Customer customer = new Customer(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName());
			accountService.createCustomer(customer);
			return customer;
		} catch (UsernameAlreadyInUseException e) {
			formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
			return null;
		} catch (Exception ex) {
			formBinding.rejectValue("username", "user.duplicateUsername", "general exception");
			return null;
		}
	}

}
