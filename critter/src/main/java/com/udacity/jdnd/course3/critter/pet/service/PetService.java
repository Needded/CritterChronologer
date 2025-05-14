package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetDTO save(Pet newPet) {
        if(newPet==null){
            throw new PetException("Pet cannot be null.");
        }

        Pet savedPet = petRepository.save(newPet);
        Customer customer = savedPet.getCustomer();

        List<Pet> customerPets = customer.getPets();
        if (customerPets == null) {
            customerPets = new ArrayList<>();
        }
        customerPets.add(savedPet);
        customer.setPets(customerPets);
        customerRepository.save(customer);

        return convertToDTO(savedPet);
    }

    public PetDTO getPetById(Long petId) {

        if(petId==null){
            throw new PetException("Pet Id cannot be null.");
        }

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException("Pet not found with ID: " + petId));

        return convertToDTO(pet);
    }

    public List<PetDTO> getPets() {

        return petRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<PetDTO> getPetByOwnerId(Long ownerId) {

        if(ownerId==null){
            throw new PetException("Owner Id cannot be null.");
        }

        return petRepository.findPetsByOwnerId(ownerId)
                .stream()
                .map(this::convertToDTO)
                .toList();

    }

    public List<Pet> getAllPetsByIds (List <Long> ids){
        return petRepository.findAllById(ids);
    }

    public PetDTO convertToDTO (Pet pet){

        PetDTO petDTO=new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());

        return petDTO;
    }
}
