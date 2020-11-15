package com.johnbryce.cs2.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.cs2.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsCustomerByEmailAndPassword(String email, String password);

	Customer findByEmailAndPassword(String email, String password);
}
