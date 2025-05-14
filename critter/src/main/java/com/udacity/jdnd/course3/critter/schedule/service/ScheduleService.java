package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.model.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.service.EmployeeException;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private EmployeeRepository employeeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
    }

    public ScheduleDTO createSchedule(Schedule newSchedule) {

        boolean allUnavailable=employeeRepository.findAll().stream()
                .allMatch(e -> e.getDaysAvailable() == null || e.getDaysAvailable().isEmpty());

        if (allUnavailable) {
            throw new EmployeeException("No employees have available days.");
        }

        return convertToDTO(scheduleRepository.save(newSchedule));
    }

    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> findPetScheduleById(Long petId) {
        if(petId==null){
            throw new ScheduleException("Schedule Id can not be null!");
        }

        return scheduleRepository.findScheduleByPetId(petId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    public List<ScheduleDTO> findEmployeeScheduleById(Long employeeId) {
        if(employeeId==null){
            throw new ScheduleException("Schedule Id can not be null!");
        }

        return scheduleRepository.findScheduleByEmployeeId(employeeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getCustomerScheduleById(Long customerId) {
        if(customerId==null){
            throw new ScheduleException("Schedule Id can not be null!");
        }

        return  scheduleRepository.findScheduleByCustomerId(customerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ScheduleDTO convertToDTO (Schedule schedule){

        ScheduleDTO scheduleDTO=new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());

        scheduleDTO.setEmployeeIds(
                schedule.getEmployees()
                        .stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList())
        );

        scheduleDTO.setPetIds(
                schedule.getPets()
                        .stream()
                        .map(Pet::getId)
                        .collect(Collectors.toList())
        );

        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getSkill());

        return scheduleDTO;

    }

}
