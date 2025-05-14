package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.CustomerDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT p.customer FROM Pet p WHERE p.id = :petId")
    Customer getCustomerByPetId(@Param("petId") Long petId);

}
