package com.johnbryce.cs2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.repo.CompanyRepository;
import com.johnbryce.cs2.repo.CouponsRepository;
import com.johnbryce.cs2.repo.CustomerRepository;

@Service
public abstract class ClientService {

	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CouponsRepository couponsRepository;

	public abstract boolean login(String email, String password) throws BadLoginException;

}
