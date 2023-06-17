package com.spring.example;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    private List<Customer> getAllCustomers (){
        return customerRepository.findAll();
    }

    record NewCustomerRequest (String name, String email, Integer age){}
    @PostMapping
    private void addNewCustomer (@RequestBody NewCustomerRequest request) {
        Customer newCustomer = new Customer ();
        newCustomer.setAge(request.age);
        newCustomer.setEmail(request.email);
        newCustomer.setName(request.name);
        customerRepository.save(newCustomer);
    }


    @DeleteMapping ("{customerId}")
    public void deleteCustomer (@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping ("{customerId}")
    public void updateCustomer (@PathVariable ("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        Customer newCustomer = customerRepository.findById(id).orElseThrow();
        newCustomer.setName(request.name != null? request.name : newCustomer.getName());
        newCustomer.setEmail(request.email != null? request.email : newCustomer.getEmail());
        newCustomer.setAge(request.age != null? request.age : newCustomer.getAge());
        customerRepository.save(newCustomer);
    }

}
