package com.johnbryce.cs2.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.cs2.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	boolean existsCompanyByEmailAndPassword(String email, String password);
	
	Company findByName(String Name);
	
	Company findByEmailAndPassword(String email, String password);
}
