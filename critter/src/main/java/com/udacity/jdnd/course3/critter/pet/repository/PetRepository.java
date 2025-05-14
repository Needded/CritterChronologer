package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet,Long> {

    @Query("SELECT p FROM Pet p WHERE p.customer.id = :customerId")
    List<Pet> findPetsByOwnerId(@Param("customerId") Long customerId);

}
