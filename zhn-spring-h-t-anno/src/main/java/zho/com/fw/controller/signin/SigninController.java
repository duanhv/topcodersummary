/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.controller.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SigninController {

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public void signin() {
		
		System.out.println("signin - blank: " + this);
		
	}
}
