/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zho.com.fw.service.AccountService;

@Controller
public class HomeController {
	
	private final Provider<ConnectionRepository> connectionRepositoryProvider;
	
	private final AccountService accountService;

	@Inject
	public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider, AccountService accountService) {
		this.connectionRepositoryProvider = connectionRepositoryProvider;
		this.accountService = accountService;
	}

	@RequestMapping("/")
	public String home(Principal currentUser, Model model) throws Exception {
		System.out.println("Test HomeController: " + this);
		model.addAttribute("connectionsToProviders", getConnectionRepository().findAllConnections());
		model.addAttribute(accountService.findAccountByUsername(currentUser.getName()));
		return "home";
	}
	
	private ConnectionRepository getConnectionRepository() {
		return connectionRepositoryProvider.get();
	}
}
