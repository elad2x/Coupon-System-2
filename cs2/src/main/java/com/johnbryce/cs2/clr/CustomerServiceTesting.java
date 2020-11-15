package com.johnbryce.cs2.clr;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.johnbryce.cs2.beans.Category;
import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.exceptions.AlreadyExistException;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.exceptions.NotExistException;
import com.johnbryce.cs2.service.CustomerService;
import com.johnbryce.cs2.utils.ArtUtil;

@Component
@Order(3)
public class CustomerServiceTesting implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;

	@Override
	public void run(String... args) throws Exception {
		ArtUtil.CustomerArt();
		customerService.setCustomerID(2);

		Coupon coupon11 = new Coupon(11, 1, Category.ELECTRICITY, "rt", "hh", Date.valueOf("2024-04-10"),
				Date.valueOf("2026-05-12"), 5, 150, "vv");
		Coupon coupon12 = new Coupon(12, 1, Category.ELECTRICITY, "rt", "hh", Date.valueOf("2024-04-10"),
				Date.valueOf("2026-05-12"), 0, 150, "vv");

		// login
		System.out.println("[========Good Login========]");
		System.out.println(customerService.login("custTwo@mail", "2222"));
		space();
		System.out.println("[========Bad login========]");
		try {
			customerService.login("asd", "43534");
		} catch (BadLoginException e) {
			e.getMessage();
		}
		space();

		// coupon purchase
		System.out.println("[========Coupon purchase good========]");
		customerService.purchaseCoupon(coupon11);
		space();
		System.out.println("[========Coupon purchase bad1========]");
		try {
			customerService.purchaseCoupon(coupon11);
		} catch (AlreadyExistException e) {
			System.out.println(e.getMessage());
		}
		space();
		System.out.println("[========Coupon purchase bad2========]");
		try {
			customerService.purchaseCoupon(coupon12);
		} catch (NotExistException e) {
			System.out.println(e.getMessage());
		}
		space();

		coupon12.setAmount(12);
		coupon12.setEndDate(Date.valueOf("2020-11-10"));
		System.out.println("[========Coupon purchase bad3========]");
		try {
			customerService.purchaseCoupon(coupon12);
		} catch (NotExistException e) {
			System.out.println(e.getMessage());
		}
		space();

		// all customer's coupons
		System.out.println("[========All customer's coupons========]");
		System.out.println(customerService.getCustomerCoupons());
		space();
		
		// customer's coupons by category
		System.out.println("[========All customer's coupons by category========]");
		System.out.println(customerService.getCustomerCoupons(Category.ELECTRICITY));
		space();
		
		// customer's coupons by c
		System.out.println("[========All customer's coupons max price========]");
		System.out.println(customerService.getCustomerCoupons(150));
		space();

		// customer's details
		System.out.println("[========Customer's details========]");
		System.out.println(customerService.getCustomerDetails());
		space();
	}

	public void space() {
		System.out.println();
	}

}
