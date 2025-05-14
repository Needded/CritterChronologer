package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetException;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO save(Customer newCustomer) {
        if (newCustomer == null) {
            throw new CustomerException("Customer cannot be null!");
        }

        if (newCustomer.getPets() != null) {
            for (Pet pet : newCustomer.getPets()) {
                pet.setCustomer(newCustomer);
            }
        }

        return convertToDTO(customerRepository.save(newCustomer));
    }

    public List<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CustomerDTO getCustomerByPet(Long petId) {
        if(petId == null){
            throw new PetException("Pet id cannot be null!");
        }

        Customer customer = customerRepository.getCustomerByPetId(petId);

        if(customer == null){
            throw new PetException("No customer found for pet id: " + petId);
        }

        return convertToDTO(customer);
    }

    public Customer getCustomerById (Long id){
        if(id == null){
            throw new PetException("Customer id cannot be null!");
        }

        return customerRepository.findById(id)
                .orElseThrow(()->new CustomerException("Customer not found by id!"));
    }


    public CustomerDTO convertToDTO (Customer customer){

        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPetIds(
                customer.getPets().stream()
                        .map(Pet::getId)
                        .toList()
        );

        return customerDTO;
    }
}
