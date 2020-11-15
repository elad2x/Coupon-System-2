package com.johnbryce.cs2.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.cs2.dailyJobs.CouponExpirationDailyJob;
import com.johnbryce.cs2.security.ClientType;
import com.johnbryce.cs2.security.LoginManager;
import com.johnbryce.cs2.service.AdministratorService;
import com.johnbryce.cs2.service.CompanyService;
import com.johnbryce.cs2.service.CustomerService;

@Component
@Scope("prototype")
@Order(5)
public class CouponExpirationDailyJobTesting implements CommandLineRunner {

	@Autowired
	private CouponExpirationDailyJob dailyJob;

	@Override
	public void run(String... args) throws Exception {

		try {

			Thread t1 = new Thread(dailyJob);
			t1.start();

			dailyJob.stop();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
