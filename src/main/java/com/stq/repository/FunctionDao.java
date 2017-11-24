package com.stq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.stq.entity.Function;

public interface FunctionDao extends PagingAndSortingRepository<Function, Long>, JpaSpecificationExecutor<Function> {

	@Modifying
	@Query(" from Function function where function.status='1' order by id desc")
	List<Function> findList();
	
	@Modifying
	@Query("select function from Function function where function.firstName=?1 and function.status='1' order by id ASC")
	List<Function> findByFirstName(String firstName);
	
	Function findBySecondName(String secondName);
	
	Function findByRole(String role);
	
	@Modifying
	@Query("select function from Function function where function.id=?1 and function.status='1'")
	Function findById(Long id);
	
}
