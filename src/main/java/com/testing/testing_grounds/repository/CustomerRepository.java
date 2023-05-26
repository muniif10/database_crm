package com.testing.testing_grounds.repository;

import com.testing.testing_grounds.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

}
