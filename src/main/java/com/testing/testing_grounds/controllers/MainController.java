package com.testing.testing_grounds.controllers;

import com.testing.testing_grounds.model.Customer;
import com.testing.testing_grounds.repository.CustomerRepository;
import com.testing.testing_grounds.repository.RegionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RegionRepository regionRepository;

    public MainController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @RequestMapping("/customers")
    public String home(Model model){
        model.addAttribute("customers", customerRepository.findAll());
        return "list";
    }
    @RequestMapping("/")
    public String homeDir(){
        return "home";
    }
    @GetMapping("/customers/card_page")
    public String beauty(Model model){
        model.addAttribute("customers",customerRepository.findAll());
        return "card_place";
    }

//    @GetMapping("/") // Refers to the URL append after domain name
//    public String helloPerson(Model model){ // Function just arbitrarily named
//
//        model.addAttribute("customers", customerRepository.findAll());
//        return "index"; // This refers to the name of the HTML template.
//    }
    @GetMapping("/customers/new_customer")
    public String new_customer_fields(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("regions",regionRepository.findAll());
        return "new_customer";
    }
    @PostMapping("/customers/add")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,Model model){

        if(customer.getRegion() == null){
//            result.addError(new ObjectError("customer","Please select region."));
            result.addError(new FieldError("customer","region","Please select a region."));
            // Returning from error removed the region list from the context. :(
            model.addAttribute("regions",regionRepository.findAll());
            return "new_customer";
        }
        if(result.hasErrors()){
            return "new_customer";
        }

        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editForm(Model model, @PathVariable Integer id){

        Customer customer = customerRepository.findById((int)id).orElseThrow(()->new IllegalArgumentException("Invalid Customer ID: " + id));
        model.addAttribute("customer", customer);
        model.addAttribute("regions",regionRepository.findAll());
        return "edit_customer";
    }
    @PostMapping("/customers/edit_customer/{id}")
    public String editCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model,@PathVariable Integer id){
        if(result.hasErrors()){
            model.addAttribute("regions",regionRepository.findAll());
            return "edit_customer";
        }
        model.addAttribute("customers",customerRepository.findAll());
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model){
        Customer customer = customerRepository.findById((int)id).orElseThrow(()-> new IllegalArgumentException("Invalid Customer ID: "+ id));
        customerRepository.delete(customer);
        model.addAttribute("customers",customerRepository.findAll());
        return "redirect:/customers";
    }




//    @PostMapping(path="/add_customer") // Map ONLY POST Requests
//    public @ResponseBody String addNewUser (@RequestParam String name
//    ,@RequestParam String address
//    ,@RequestParam String department
//    ,@RequestParam Integer regionId
//    ) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//
//        Customer n = new Customer();
//        n.setName(name);
//        n.setAddress(address);
//        n.setDepartment(department);
//        n.setRegion_id(regionId);
//        customerRepository.save(n);
//        return "Saved";
//    }

    @GetMapping(path="list")
    public @ResponseBody Iterable<Customer> getAllPerson() {
        // This returns a JSON or XML with the users
        return customerRepository.findAll();
    }
}
