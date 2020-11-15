package com.johnbryce.cs2.service;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.johnbryce.cs2.beans.Category;
import com.johnbryce.cs2.beans.Company;
import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.exceptions.AlreadyExistException;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.exceptions.UnableToUpdateException;
import lombok.Data;

@Service
@Data
public class CompanyService extends ClientService {

	private int companyID;

	public CompanyService() {
		super();
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	@Override
	public boolean login(String email, String password) throws BadLoginException {
		if (companyRepository.existsCompanyByEmailAndPassword(email, password) == false) {
			throw new BadLoginException("Either email or password is incorrect");
		}
		return companyRepository.existsCompanyByEmailAndPassword(email, password);
	}

	public void addCoupon(Coupon coupon) throws AlreadyExistException {
		for (Coupon coup : getCompanyCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle())) {
				throw new AlreadyExistException("This coupon already exist");
			}
		}

		Company company = companyRepository.getOne(this.companyID);
		company.getCoupons().add(coupon);
		companyRepository.saveAndFlush(company);
		couponsRepository.save(coupon);

	}

	public void updateCoupon(Coupon coupon) throws UnableToUpdateException {
		for (Coupon coup : getCompanyCoupons()) {
			if (coup.getId() == coupon.getId() && coup.getCompanyID() != coupon.getCompanyID()) {
				throw new UnableToUpdateException("Coupon's id and compnyID can not be changed");
			}
		}
		couponsRepository.saveAndFlush(coupon);
	}

	public void deleteCoupon(int couponID) {
		couponsRepository.deleteCouponPurchaseByCouponID(couponID);
		couponsRepository.deleteById(couponID);
	}

	public List<Coupon> getCompanyCoupons() {
		List<Coupon> coupons = couponsRepository.findAll();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coup : coupons) {
			if (coup.getCompanyID() == this.companyID) {
				results.add(coup);
			}
		}

		return results;
	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) {
		List<Coupon> coupons = couponsRepository.findAll();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coup : coupons) {
			if (coup.getCompanyID() == this.companyID && coup.getCategory().equals(category)) {
				results.add(coup);
			}
		}

		return (ArrayList<Coupon>) results;

	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
		List<Coupon> coupons = couponsRepository.findAll();
		List<Coupon> results = new ArrayList<>();
		for (Coupon coup : coupons) {
			if (coup.getCompanyID() == this.companyID && coup.getPrice() <= maxPrice) {
				results.add(coup);
			}
		}

		return (ArrayList<Coupon>) results;

	}

	public Company getCompanyDetails() {
		return companyRepository.getOne(companyID);
	}

	public int getIdFromDB(String email, String password) {
		return companyRepository.findByEmailAndPassword(email, password).getId();
	}

//	private Coupon getOneCoupon(int couponID) {
//		return couponsRepository.getOne(couponID);
//	}
//
//	private Coupon getOneCouponV2(int companyID) {
//		return couponsRepository.findByCompanyID(companyID);
//	}

//	public void updateCoupon(Coupon coupon) throws UnableToUpdateException {
//	if (getOneCoupon(coupon.getId()) == null) {
//		throw new UnableToUpdateException("Coupon's id can not be updated");
//	}
//	if (getOneCouponV2(coupon.getCompanyID()) == null) {
//		throw new UnableToUpdateException("Coupon's companyID can not be updated");
//	}
//	couponsRepository.saveAndFlush(coupon);
//}

}
