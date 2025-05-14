package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.model.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private EmployeeService employeeService;
    private PetService petService;

    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService, PetService petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PutMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule newSchedule= new Schedule();
        BeanUtils.copyProperties(scheduleDTO, newSchedule);

        List<Employee> employees=employeeService.getAllEmployeeByIds(scheduleDTO.getEmployeeIds());
        List<Pet> pets=petService.getAllPetsByIds(scheduleDTO.getPetIds());


        newSchedule.setEmployees(employees);
        newSchedule.setPets(pets);
        newSchedule.setDate(scheduleDTO.getDate());
        newSchedule.setSkill(scheduleDTO.getActivities());

        return scheduleService.createSchedule(newSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findPetScheduleById(petId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findEmployeeScheduleById(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getCustomerScheduleById(customerId);
    }
}
