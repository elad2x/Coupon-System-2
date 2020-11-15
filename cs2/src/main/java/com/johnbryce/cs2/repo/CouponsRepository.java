package com.johnbryce.cs2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.johnbryce.cs2.beans.Coupon;

public interface CouponsRepository extends JpaRepository<Coupon, Integer> {

	Coupon findByCompanyID(int companyID);

	@Transactional
	@Modifying
	@Query(value = "DELETE from customers_coupons WHERE coupons_id=:couponID", nativeQuery = true)
	void deleteCouponPurchaseByCouponID(int couponID);

	@Transactional
	@Modifying
	@Query(value = "DELETE from customers_coupons WHERE customer_id=:customerID", nativeQuery = true)
	void deleteCouponPurchaseByCustomerID(int customerID);

}
