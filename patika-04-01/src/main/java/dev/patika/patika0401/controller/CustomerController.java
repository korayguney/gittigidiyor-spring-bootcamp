package dev.patika.patika0401.controller;

import dev.patika.patika0401.dto.CustomerDTO;
import dev.patika.patika0401.exceptions.BadRequestException;
import dev.patika.patika0401.model.Customer;
import dev.patika.patika0401.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/save-customer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid CustomerDTO customerdto){
        Optional<Customer> resultOptional = customerService.saveCustomer(customerdto);
        if(resultOptional.isPresent()){
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> update(CustomerDTO customerDTO){
            return ResponseEntity.ok(customerService.update(customerDTO));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id){
        customerService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable long id){
       return ResponseEntity.ok(customerService.getById(id));
    }



}
