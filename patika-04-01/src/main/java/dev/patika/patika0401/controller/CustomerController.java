package dev.patika.patika0401.controller;

import dev.patika.patika0401.dto.CustomerDTO;
import dev.patika.patika0401.exceptions.CustomerNotFoundException;
import dev.patika.patika0401.model.Customer;
import dev.patika.patika0401.service.CustomerService;
import dev.patika.patika0401.util.ClientRequestInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ClientRequestInfo clientRequestInfo;

    @PostMapping("/save-customer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid CustomerDTO customerdto){
        Optional<Customer> resultOptional = customerService.saveCustomer(customerdto);
        if(resultOptional.isPresent()){
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testError() {
        throw new IllegalArgumentException("not foundddd...");
           // return new ResponseEntity<>("test error", HttpStatus.OK);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception e) {
        String errorId = UUID.randomUUID().toString();
        if (e != null) {
            String logMessage = format("PAYMENT_ENGINE_REDIRECT_ERROR: errorId: {0}, message: {1}", errorId, e.getMessage());
        }
        return new ResponseEntity<>(errorId, HttpStatus.OK);
    }
}
