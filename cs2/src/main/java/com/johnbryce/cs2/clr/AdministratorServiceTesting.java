package com.johnbryce.cs2.clr;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.cs2.beans.Category;
import com.johnbryce.cs2.beans.Company;
import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.beans.Customer;
import com.johnbryce.cs2.exceptions.AlreadyExistException;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.exceptions.UnableToUpdateException;
import com.johnbryce.cs2.service.AdministratorService;
import com.johnbryce.cs2.utils.ArtUtil;

@Component
@Order(1)
public class AdministratorServiceTesting implements CommandLineRunner {

	@Autowired
	private AdministratorService administratorService;

	@Override
	public void run(String... args) throws Exception {
		ArtUtil.AdministratorArt();

		Date date1 = Date.valueOf("2020-11-15");
		Date date2 = Date.valueOf("2023-11-22");
		Date date3 = Date.valueOf("2021-12-28");
		Date date4 = Date.valueOf("2022-10-22");
		Date date5 = Date.valueOf("2022-07-18");
		Date date6 = Date.valueOf("2024-10-12");

		Coupon coupon1 = new Coupon(1, 1, Category.ELECTRICITY, "aa", "tt", date1, date3, 10, 100, "vv");
		Coupon coupon2 = new Coupon(2, 1, Category.FOOD, "bb", "yy", date4, date6, 15, 150, "vv");
		Coupon coupon3 = new Coupon(3, 2, Category.VACATION, "cc", "uu", date1, date4, 15, 220, "vv");
		Coupon coupon4 = new Coupon(4, 2, Category.ELECTRICITY, "dd", "ii", date2, date6, 30, 160, "vv");
		Coupon coupon5 = new Coupon(5, 3, Category.RESTURANT, "ee", "pp", date5, date2, 25, 170, "vv");
		Coupon coupon6 = new Coupon(6, 3, Category.VACATION, "ff", "oo", date5, date3, 30, 200, "vv");

		List<Coupon> coupons1 = new ArrayList<>();
		coupons1.add(coupon1);
		coupons1.add(coupon2);
		List<Coupon> coupons2 = new ArrayList<>();
		coupons2.add(coupon3);
		coupons2.add(coupon4);
		List<Coupon> coupons3 = new ArrayList<>();
		coupons3.add(coupon5);
		coupons3.add(coupon6);

		Company company1 = new Company(1, "CompNameOne", "one@mail", "1111", coupons1);
		Company company2 = new Company(2, "CompNameTwo", "two@mail", "2222", coupons2);
		Company company3 = new Company(3, "CompNameThree", "three@mail", "3333", coupons3);

		Customer customer1 = new Customer(1, "CustNameOne", "LastNameOne", "custOne@mail", "1111", coupons1);
		Customer customer2 = new Customer(2, "CustNameTwo", "LastNameTwo", "custTwo@mail", "2222", coupons2);
		Customer customer3 = new Customer(3, "CustNameThree", "LastNameThree", "custThree@mail", "3333", coupons3);

		// login
		System.out.println("[========Good login========]");
		System.out.println(administratorService.login("admin@admin.com", "admin"));
		space();
		System.out.println("[========Bad login========]");
		try {
			administratorService.login("aaa", "bbb");
		} catch (BadLoginException e) {
			System.out.println(e.getMessage());
		}
		space();
		
		// add company
		System.out.println("[========Add company good========]");
		administratorService.addCompany(company1);
		administratorService.addCompany(company2);
		administratorService.addCompany(company3);
		space();
		System.out.println("[========Add company bad========]");
		try {
			administratorService.addCompany(company1);
		} catch (AlreadyExistException e) {
			System.out.println(e.getMessage());
		}
		space();
		
		// update company
		System.out.println("[========Update company good========]");
		company2.setPassword("2121");
		administratorService.updateCompany(company2);
		space();
		System.out.println("[========Update company bad1========]");
		company2.setName("lll");
		try {
			administratorService.updateCompany(company2);
		} catch (UnableToUpdateException e) {
			System.out.println(e.getMessage());
		}
		space();
		
		company2.setName("CompNameTwo");
		System.out.println("[========Update company bad2========]");
		company2.setId(14);
		try {
			administratorService.updateCompany(company2);
		} catch (UnableToUpdateException e) {
			System.out.println(e.getMessage());
		}
		company2.setId(2);
		space();
		
		System.out.println("[========Update company bad3========]");
		company2.setName("asd");
		company2.setId(7);
		try {
			administratorService.updateCompany(company2);
		} catch (UnableToUpdateException e) {
			System.out.println(e.getMessage());
		}
		company2.setName("CompNameTwo");
		company2.setId(2);
		space();
		
		// get all companies
		System.out.println("[========All compines========]");
		System.out.println(administratorService.getAllCompanies());
		space();
		
		// get one company
		System.out.println("[========One company========]");
		System.out.println(administratorService.getOneCompany(company1.getId()));
		space();
		
		// add customer
		System.out.println("[========Add customer good========]");
		administratorService.addCustomer(customer1);
		administratorService.addCustomer(customer2);
		administratorService.addCustomer(customer3);
		space();
		System.out.println("[========Add customer bad========]");
		try {
			administratorService.addCustomer(customer2);
		} catch (AlreadyExistException e) {
			System.out.println(e.getMessage());
		}
		space();

		// update customer
		System.out.println("[========Update customer good========]");
		customer3.setLastname("moshe");
		administratorService.updateCustomer(customer3);
		space();
		customer3.setLastname("LastNameThree");
		System.out.println("[========Update customer bad========]");
		customer3.setId(5);
		try {
			administratorService.updateCustomer(customer3);
		} catch (UnableToUpdateException e) {
			System.out.println(e.getMessage());
		}
		customer3.setId(3);
		space();

		// delete company
		System.out.println("[========Delete company========]");
		administratorService.deleteCompany(company3.getId());
		space();
//		System.out.println("--------Companies after delete--------");
//		System.out.println(administratorService.getAllCompanies());
//		System.out.println("--------Restore deleted company--------");
//		administratorService.addCompany(company3);;

		// get all customers
		System.out.println("[========All customers========]");
		System.out.println(administratorService.getAllCustomers());
		space();

		// get one customer
		System.out.println("[========One customer========]");
		System.out.println(administratorService.getOneCustomer(customer1.getId()));
		space();
		
		// delete customer
		System.out.println("[========Delete customer========]");
		administratorService.deleteCustomer(customer3.getId());
		space();
//		System.out.println("--------Customers after delete--------");
//		System.out.println(administratorService.getAllCustomers());
//		System.out.println("--------Restore deleted company--------");
//		administratorService.addCustomer(customer3);

	}

	public void space() {
		System.out.println();
	}

}
