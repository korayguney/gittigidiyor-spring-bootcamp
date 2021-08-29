package dev.patika.patika0401.service;

import dev.patika.patika0401.dto.CustomerDTO;
import dev.patika.patika0401.exceptions.BadRequestException;
import dev.patika.patika0401.exceptions.CustomerNotFoundException;
import dev.patika.patika0401.mappers.CustomerMapper;
import dev.patika.patika0401.model.Customer;
import dev.patika.patika0401.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public Optional<Customer> saveCustomer(CustomerDTO customerDTO){

        boolean isExists = customerRepository.selectExistsSsid(customerDTO.getSsid());

        if(isExists){
            throw new BadRequestException("Customer with SSID : " + customerDTO.getSsid() + " is already exists!");
        }

        /*
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setSsid(customerDTO.getSsid());
        customer.setEmail(customerDTO.getEmail());
         */

        Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);

        return Optional.of(customerRepository.save(customer));
    }
    @Transactional
    public void delete(long id){
        if(!customerRepository.existsById(id)){
            throw new CustomerNotFoundException("Customer not found.");
        }
        customerRepository.deleteById(id);
    }
    @Transactional
    public Customer update(CustomerDTO customerDTO){
        if(!customerRepository.existsById(customerDTO.getId())){
            throw new CustomerNotFoundException("Customer not found.");
        }
       return customerRepository.save(customerMapper.mapFromCustomerDTOtoCustomer(customerDTO));
    }
    public Customer getById(long id){
      return   customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer not found."));
    }

}
