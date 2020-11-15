package com.johnbryce.cs2.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.johnbryce.cs2.beans.Category;
import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.beans.Customer;
import com.johnbryce.cs2.exceptions.AlreadyExistException;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.exceptions.NotExistException;

import lombok.Data;

@Service
@Data
public class CustomerService extends ClientService {
	
	private int customerID;
	
	@Override
	public boolean login(String email, String password) throws BadLoginException {
		if (customerRepository.existsCustomerByEmailAndPassword(email, password) == false) {
			throw new BadLoginException("Either email or password is incorrect");
		}
		return customerRepository.existsCustomerByEmailAndPassword(email, password);
	}

	public void purchaseCoupon(Coupon coupon) throws AlreadyExistException, NotExistException {
		Date currentDate = new Date(System.currentTimeMillis());
		for (Coupon coup : getCustomerCoupons()) {
			if (coup.getId() == coupon.getId()) {
				throw new AlreadyExistException("Can not purchase the same coupon more than once");
			}
		}

		if (coupon.getAmount() <= 0) {
			throw new NotExistException("This coupns is currently unavailable");
		}
		if (coupon.getEndDate().before(currentDate)) {
			throw new NotExistException("The coupon is expired");
		}

		Customer customer = customerRepository.getOne(customerID);
		coupon.setAmount(coupon.getAmount() - 1);
		couponsRepository.saveAndFlush(coupon);
		customer.getCoupons().add(coupon);
		customerRepository.saveAndFlush(customer);

	}

	public List<Coupon> getCustomerCoupons() {
		return customerRepository.getOne(customerID).getCoupons();
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory() == category) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= maxPrice) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public Customer getCustomerDetails() {
		return customerRepository.getOne(this.customerID);
	}

	public int getIdFromDB(String email, String password) {
		return customerRepository.findByEmailAndPassword(email, password).getId();
	}

}
