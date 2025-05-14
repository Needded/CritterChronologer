package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.model.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.model.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {

private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployeeByIds(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }

    public EmployeeDTO save(Employee newEmployee) {

        if(newEmployee==null){
            throw new EmployeeException("Employee cannot be null!");
        }

        return convertToDTO(employeeRepository.save(newEmployee));
    }

    public EmployeeDTO getEmployeeById(Long employeeId) {
        if(employeeId==null){
            throw new EmployeeException("Employee Id cannot be null!");
        }
        Employee employee= employeeRepository.findById(employeeId)
                .orElseThrow(()->new EmployeeException("Employee not found!"));

        return convertToDTO(employee);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {

        if(employeeId==null){
            throw new EmployeeException("Employee Id cannot be null!");
        }
        if (daysAvailable == null) {
            throw new EmployeeException("Days available cannot be null!");
        }


        employeeRepository.findById(employeeId)
                .map(e -> {
                    e.setDaysAvailable(daysAvailable);
                    return employeeRepository.save(e);
                })
                .orElseThrow(() -> new EmployeeException("Employee not found!"));
    }

    public List<EmployeeDTO> getEmployeeAvailability(EmployeeRequestDTO employeeDTO) {

        if(employeeDTO==null){
            throw new EmployeeException("Employee cannot be null!");
        }

        List<Employee> allEmployee = employeeRepository.findAll()
                .stream()
                .filter(e -> e.getSkills().containsAll(employeeDTO.getSkills())
                        && e.getDaysAvailable().contains(employeeDTO.getDate().getDayOfWeek()))
                .toList();

        return allEmployee.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public EmployeeDTO convertToDTO (Employee employee){

        EmployeeDTO employeeDTO= new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());

        return employeeDTO;
    }
}
