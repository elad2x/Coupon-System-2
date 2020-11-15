package com.johnbryce.cs2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.johnbryce.cs2.beans.Coupon;
import com.johnbryce.cs2.beans.Customer;
import com.johnbryce.cs2.beans.Company;
import com.johnbryce.cs2.exceptions.BadLoginException;
import com.johnbryce.cs2.exceptions.UnableToUpdateException;
import com.johnbryce.cs2.exceptions.AlreadyExistException;

import lombok.Data;

@Service
@Data
public class AdministratorService extends ClientService {

	@Override
	public boolean login(String email, String password) throws BadLoginException {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		}
		throw new BadLoginException("Either email or password are wrong");
	}

	public void addCompany(Company company) throws AlreadyExistException {
		for (Company comp : getAllCompanies()) {
			if (comp.getName().equals(company.getName()) || comp.getEmail().equals(company.getEmail())) {
				throw new AlreadyExistException("Company's name or email already exist");
			}
		}
		companyRepository.save(company);

	}

	public void updateCompany(Company company) throws UnableToUpdateException {
		boolean flag = false;
		for (Company comp : getAllCompanies()) {
			if (comp.getId() == company.getId() && !comp.getName().equals(company.getName())) {
				throw new UnableToUpdateException("Updating company's name is unpermitted");
			} else if (comp.getName().equals(company.getName()) && comp.getId() != company.getId()) {
				throw new UnableToUpdateException("Updating company's id is unpermitted");
			}
			if (comp.getId() != company.getId() && !comp.getName().equals(company.getName())) {
				flag = true;
			} else if (comp.getId() == company.getId() && comp.getName().equals(company.getName())) {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			throw new UnableToUpdateException("Updating company's id and name is unpermitted");
		} else {
			companyRepository.saveAndFlush(company);
		}
	}

	public void deleteCompany(int companyID) {
		for (Coupon coup : getOneCompany(companyID).getCoupons()) {
			couponsRepository.deleteCouponPurchaseByCouponID(coup.getId());
		}
		companyRepository.deleteById(companyID);
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company getOneCompany(int companyID) {
		return companyRepository.getOne(companyID);
	}

	public void addCustomer(Customer customer) throws AlreadyExistException {
		for (Customer cust : getAllCustomers()) {
			if (cust.getEmail().equals(customer.getEmail())) {
				throw new AlreadyExistException("This customer's email already exist");
			}
		}
		customerRepository.save(customer);
	}

	public void updateCustomer(Customer customer) throws UnableToUpdateException {
		boolean flag = false;
		for (Customer cust : getAllCustomers()) {
			if (cust.getId() != customer.getId()) {
				flag = true;
			} else if (cust.getId() == customer.getId()) {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			throw new UnableToUpdateException("Updating customer's id is unpermitted");
		} else {
			customerRepository.saveAndFlush(customer);
		}

	}

	public void deleteCustomer(int customerID) {
		couponsRepository.deleteCouponPurchaseByCustomerID(customerID);
		customerRepository.deleteById(customerID);
	}

	public ArrayList<Customer> getAllCustomers() {
		return (ArrayList<Customer>) customerRepository.findAll();
	}

	public Customer getOneCustomer(int customerID) {
		return customerRepository.getOne(customerID);
	}

}
