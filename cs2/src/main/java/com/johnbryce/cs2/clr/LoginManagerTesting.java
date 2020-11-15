package com.johnbryce.cs2.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.cs2.security.ClientType;
import com.johnbryce.cs2.security.LoginManager;
import com.johnbryce.cs2.service.AdministratorService;
import com.johnbryce.cs2.service.CompanyService;
import com.johnbryce.cs2.service.CustomerService;
import com.johnbryce.cs2.utils.ArtUtil;

@Component
@Scope("prototype")
@Order(4)
public class LoginManagerTesting implements CommandLineRunner {

	@Autowired
	private LoginManager loginManager;

	@Override
	public void run(String... args) throws Exception {
		ArtUtil.LoginArt();
		space();
		System.out.println("[========Get all companies int the system========]");
		AdministratorService administratorService = (AdministratorService) loginManager.login("admin@admin.com",
				"admin", ClientType.Administrator);
		System.out.println(administratorService.getAllCompanies());
		space();

		System.out.println("[========Get all company's coupons========]");
		CompanyService companyService = (CompanyService) loginManager.login("one@mail", "1111", ClientType.Company);
		System.out.println(companyService.getCompanyCoupons());
		space();

		System.out.println("[========Get customer's details========]");
		CustomerService customerService = (CustomerService) loginManager.login("custOne@mail", "1111",
				ClientType.Customer);
		System.out.println(customerService.getCustomerDetails());
		space();
	}

	public void space() {
		System.out.println();
	}

}
