package com.johnbryce.cs2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.service.AdministratorService;
import com.johnbryce.cs2.service.ClientService;
import com.johnbryce.cs2.service.CompanyService;
import com.johnbryce.cs2.service.CustomerService;

@Component
@Lazy
public class LoginManager {

	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CustomerService customerService;

	public ClientService login(String email, String password, ClientType clienttype) throws BadLoginException {
		switch (clienttype) {
		case Administrator:
			if (email.equals("admin@admin.com") && password.equals("admin")) {
				return administratorService;
			}
			break;
		case Company:
			if (companyService.login(email, password)) {
				companyService.setCompanyID(companyService.getIdFromDB(email, password));
				return companyService;
			}
			break;

		case Customer:

			if (customerService.login(email, password)) {
				customerService.setCustomerID(customerService.getIdFromDB(email, password));
				return customerService;
			}
			break;

		default:
			break;
		}
		return null;
	}
}
