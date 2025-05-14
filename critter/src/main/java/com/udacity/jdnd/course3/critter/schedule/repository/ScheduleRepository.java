package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.model.ScheduleDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.id = :petId")
    List<Schedule> findScheduleByPetId(@Param("petId") Long petId);

    @Query("SELECT s FROM Schedule s JOIN s.employees e WHERE e.id = :employeeId")
    List<Schedule> findScheduleByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.customer.id = :customerId")
    List<Schedule> findScheduleByCustomerId(@Param("customerId") Long customerId);

}
