package com.johnbryce.cs2.dailyJobs;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.repo.CouponsRepository;
import com.johnbryce.cs2.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
public class CouponExpirationDailyJob implements Runnable {

	@Autowired
	private CouponsRepository couponsRepository;

	@Autowired
	private CompanyService companyService;

	private boolean quit;

	public CouponExpirationDailyJob() {
		this.quit = false;
	}

	public boolean isQuit() {
		return quit;
	}

	@Override
	public void run() {
		while (!quit) { // while quit = false
			for (Coupon coupon : couponsRepository.findAll()) {
				if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
					couponsRepository.deleteCouponPurchaseByCouponID(coupon.getId());
					companyService.setCompanyID(coupon.getCompanyID());
					companyService.deleteCoupon(coupon.getId());
					System.out.println("Expired coupon deleted");
				}
			}
			try {
				Thread.sleep(1000 * 60 * 60 * 24);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void stop() {
		this.quit = !quit;
	}

}
