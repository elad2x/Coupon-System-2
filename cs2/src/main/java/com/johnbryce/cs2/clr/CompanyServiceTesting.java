package com.johnbryce.cs2.clr;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.cs2.beans.Category;
import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.beans.Customer;
import com.johnbryce.cs2.exceptions.AlreadyExistException;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.exceptions.UnableToUpdateException;
import com.johnbryce.cs2.service.AdministratorService;
import com.johnbryce.cs2.service.CompanyService;
import com.johnbryce.cs2.utils.ArtUtil;

@Component
@Order(2)
public class CompanyServiceTesting implements CommandLineRunner {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AdministratorService administratorService;

	@Override
	public void run(String... args) throws Exception {
		ArtUtil.CompanyArt();
		companyService.setCompanyID(1);
		space();

		Date date1 = Date.valueOf("2022-10-22");
		Date date2 = Date.valueOf("2024-10-12");

		Coupon coupon7 = new Coupon(7, 1, Category.VACATION, "gfd", "hgh", Date.valueOf("2022-08-10"),
				Date.valueOf("2024-10-12"), 5, 80, "vv");
		Coupon coupon8 = new Coupon(8, 1, Category.RESTURANT, "arty", "sd", Date.valueOf("2021-06-12"),
				Date.valueOf("2023-10-11"), 5, 300, "vv");
		Coupon coupon9 = new Coupon(9, 1, Category.FOOD, "gfwerd", "hy", Date.valueOf("2023-04-10"),
				Date.valueOf("2024-05-12"), 5, 150, "vv");
		Coupon coupon10 = new Coupon(10, 1, Category.VACATION, "aarr", "qq", Date.valueOf("2021-01-01"),
				Date.valueOf("2024-05-19"), 5, 150, "vv");
		Coupon coupon11 = new Coupon(11, 1, Category.ELECTRICITY, "rt", "hh", Date.valueOf("2024-04-10"),
				Date.valueOf("2026-05-12"), 5, 150, "vv");

		List<Coupon> coupons = new ArrayList<>();
		coupons.add(coupon10);
		coupons.add(coupon11);

		Customer customer4 = new Customer(4, "Avi", "Levi", "avi@mail", "432", coupons);
		administratorService.addCustomer(customer4);

		// exiting coupon
		Coupon couponTemp = new Coupon(2, 1, Category.FOOD, "bb", "yy", date1, date2, 15, 150, "vv");

		// login
		System.out.println("[========Good login========]");
		System.out.println(companyService.login("one@mail", "1111"));
		space();
		System.out.println("[========Bad login========]");
		try {
			System.out.println(companyService.login("wwww", "554"));
		} catch (BadLoginException e) {
			System.out.println(e.getMessage());
		}
		space();

		// add coupon
		System.out.println("[========Add coupon good========]");
		companyService.addCoupon(coupon7);
		companyService.addCoupon(coupon8);
		companyService.addCoupon(coupon9);
		space();
		System.out.println("[========Add coupon bad========]");
		try {
			companyService.addCoupon(couponTemp);
		} catch (AlreadyExistException e) {
			System.out.println(e.getMessage());
		}
		space();

		// update coupon
		System.out.println("[========Update coupon good========]");
		coupon7.setImage("rr");
		try {
			companyService.updateCoupon(coupon7);
		} catch (UnableToUpdateException e) {
			System.out.println(e.getMessage());
		}
		space();

		System.out.println("[========Update coupon bad========]");
		coupon7.setCompanyID(2);
		try {
			companyService.updateCoupon(coupon7);
		} catch (UnableToUpdateException e) {
			System.out.println(e.getMessage());
		}
		coupon7.setCompanyID(1);
		space();

		// get all company's coupons
		System.out.println("[========All company's coupons========]");
		System.out.println(companyService.getCompanyCoupons());
		space();
		System.out.println("[========All company's coupons by Category========]");
		System.out.println(companyService.getCompanyCoupons(Category.RESTURANT));
		space();
		System.out.println("[========All company's coupons by Max Price========]");
		System.out.println(companyService.getCompanyCoupons(200));
		space();

		// company details
		System.out.println("[========Company details========]");
		System.out.println(companyService.getCompanyDetails());
		space();

		// delete coupon
		System.out.println("[========Delete coupon========]");
		companyService.deleteCoupon(coupon7.getId());
		space();

//		System.out.println("***********get company id*********");
//		System.out.println(companyService.getIdFromDB("one@mail", "1111"));
//	
	}

	public void space() {
		System.out.println();
	}

}
